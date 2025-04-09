package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

@Dao
interface LocationDAO {
    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE}")
    fun getLocations(): Flow<List<LocationObject>>

    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE} WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(locations: List<LocationObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)

    @Query("DELETE FROM ${RMDatabase.LOCATION_TABLE}")
    suspend fun clear()

}