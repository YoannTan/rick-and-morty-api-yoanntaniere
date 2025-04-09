package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview
import org.mathieu.cleanrmapi.domain.character.models.Character

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

internal fun LocationObject.toModel(residents: List<Character>): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents
    )
}

internal fun LocationObject.toPreview(): LocationPreview {
    return LocationPreview(
        id = id,
        name = name,
        locationId = id
    )
}


