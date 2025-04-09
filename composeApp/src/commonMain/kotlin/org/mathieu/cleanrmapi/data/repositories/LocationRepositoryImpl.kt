package org.mathieu.cleanrmapi.data.repositories

import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.DataStore
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location

private const val LOCATION_CACHE_DURATION = 1000 * 60 * 60 * 24 // 24h

/**
 * Implementation of [LocationRepository] that handles data retrieval for locations
 * using both local cache (Room) and remote API as fallback.
 *
 * @property dataStore Provides access to persistent key-value storage.
 * @property locationApi API interface used to fetch remote location data.
 * @property locationDAO Data access object for local location data.
 * @property characterDAO DAO used to retrieve resident character details.
 */
internal class LocationRepositoryImpl(
    private val dataStore: DataStore,
    private val locationApi: LocationApi,
    private val locationDAO: LocationDAO,
    private val characterDAO: CharacterDAO
) : LocationRepository {

    /**
     * Retrieves a location by its ID, using cache if available and fresh, or falling back to the remote API.
     * Also resolves all resident character references from local storage.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return A fully populated [Location] object, including residents.
     * @throws Exception If the location cannot be retrieved from either cache or API.
     */
    override suspend fun getLocation(id: Int): Location {
        val cached = locationDAO.getLocation(id)

        val isStale = cached == null || isExpired(cached.lastFetchedAt)

        val locationObject = if (isStale) {
            val response = locationApi.getLocation(id)
            val obj = LocationObject.fromResponse(response)
            locationDAO.insert(obj)
            obj
        } else {
            cached
        }

        if (locationObject != null) {
            val residents = locationObject.residentIds.mapNotNull {
                characterDAO.getCharacter(it)?.toModel()
            }

            return locationObject.toModel(residents)
        }

        throw Exception("Could not load location $id.")
    }

    /**
     * Determines if a cached location entry is expired based on a fixed duration.
     *
     * @param lastFetchedAt The timestamp of the last data fetch in milliseconds.
     * @return True if the data is considered stale, false otherwise.
     */
    private fun isExpired(lastFetchedAt: Long): Boolean {
        return System.currentTimeMillis() - lastFetchedAt > LOCATION_CACHE_DURATION
    }

}