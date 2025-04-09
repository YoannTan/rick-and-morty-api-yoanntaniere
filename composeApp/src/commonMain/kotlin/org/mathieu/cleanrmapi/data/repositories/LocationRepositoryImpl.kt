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

internal class LocationRepositoryImpl(
    private val dataStore: DataStore,
    private val locationApi: LocationApi,
    private val locationDAO: LocationDAO,
    private val characterDAO: CharacterDAO
) : LocationRepository {

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

    private fun isExpired(lastFetchedAt: Long): Boolean {
        return System.currentTimeMillis() - lastFetchedAt > LOCATION_CACHE_DURATION
    }

}