package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

/**
 * Data Access Object (DAO) for accessing and managing [LocationObject] entities in the database.
 */
@Dao
interface LocationDAO {

    /**
     * Retrieves all locations stored in the database as a [Flow] of list.
     *
     * @return A [Flow] emitting the list of all [LocationObject] entities.
     */
    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE}")
    fun getLocations(): Flow<List<LocationObject>>

    /**
     * Retrieves a specific location by its ID.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [LocationObject] with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE} WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    /**
     * Inserts a list of locations into the database. If a location already exists, it is replaced.
     *
     * @param locations The list of [LocationObject]s to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(locations: List<LocationObject>)

    /**
     * Inserts or replaces a single location in the database.
     *
     * @param location The [LocationObject] to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)

    /**
     * Deletes all locations from the database.
     */
    @Query("DELETE FROM ${RMDatabase.LOCATION_TABLE}")
    suspend fun clear()

}