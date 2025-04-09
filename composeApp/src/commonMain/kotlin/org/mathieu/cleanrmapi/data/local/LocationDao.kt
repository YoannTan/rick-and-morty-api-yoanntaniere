package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

@Dao
interface LocationDAO {
    suspend fun getLocation(id: Int): LocationObject?
    suspend fun insert(location: LocationObject)
    suspend fun getAll(): List<LocationObject>
    suspend fun clear()
}