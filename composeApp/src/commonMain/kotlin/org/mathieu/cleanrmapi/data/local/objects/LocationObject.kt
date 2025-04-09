package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview
import org.mathieu.cleanrmapi.domain.character.models.Character

/**
 * Room entity representing a location as stored in the local database.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type Type or category of the location.
 * @property dimension Dimension or universe where the location exists.
 * @property residentIds List of character IDs that reside in this location.
 * @property created Date when the location was originally created (from the API).
 * @property lastFetchedAt Timestamp of the last time this data was fetched from the API.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>,
    val created: String,
    val lastFetchedAt: Long
) {
    companion object {
        /**
         * Creates a [LocationObject] from a [LocationResponse].
         *
         * @param response The response received from the remote API.
         * @return A local database object corresponding to the response.
         */
        fun fromResponse(response: LocationResponse): LocationObject {
            val ids = response.residents.mapNotNull {
                it.substringAfterLast("/").toIntOrNull()
            }

            return LocationObject(
                id = response.id,
                name = response.name,
                type = response.type,
                dimension = response.dimension,
                residentIds = ids,
                created = response.created,
                lastFetchedAt = System.currentTimeMillis()
            )
        }
    }
}

/**
 * Converts a [LocationResponse] to a [LocationObject] for local storage.
 *
 * @return A [LocationObject] instance populated with the data from this response.
 */
internal fun LocationResponse.toDBObject(): LocationObject {
    val residentIds = residents.mapNotNull { url ->
        url.substringAfterLast("/").toIntOrNull()
    }

    return LocationObject(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residentIds = residentIds,
        created = created,
        lastFetchedAt = System.currentTimeMillis()
    )
}

/**
 * Converts a [LocationObject] to a domain model [Location], including resolved residents.
 *
 * @param residents The list of [Character]s who reside at this location.
 * @return A [Location] domain model instance.
 */
internal fun LocationObject.toModel(residents: List<Character>): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents
    )
}

/**
 * Converts a [LocationObject] into a lightweight [LocationPreview] model.
 *
 * @return A simplified version of the location, for use in summaries or previews.
 */
internal fun LocationObject.toPreview(): LocationPreview {
    return LocationPreview(
        id = id,
        name = name,
        locationId = id
    )
}


