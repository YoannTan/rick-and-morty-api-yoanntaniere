package org.mathieu.cleanrmapi.domain.location

import org.mathieu.cleanrmapi.domain.location.models.Location

/**
 * Defines the contract for accessing location-related data from a data source (e.g., API or local database).
 */
interface LocationRepository {

    /**
     * Retrieves detailed information about a specific location by its unique identifier.
     *
     * @param id The unique identifier of the location.
     * @return A [Location] object containing detailed information.
     */
    suspend fun getLocation(id: Int): Location
}