package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse
import org.mathieu.cleanrmapi.data.validators.IdListValidator
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds

/**
 * Provides methods to fetch location data from the remote Rick & Morty API.
 *
 * This class is responsible for handling all HTTP requests related to location resources.
 * It uses the injected [HttpClient] to perform API calls and deserialize the responses.
 *
 * @property client The [HttpClient] used to make network requests.
 */
internal class LocationApi(private val client: HttpClient) {

    /**
     * Fetches a list of locations from the API.
     *
     * @param page The page number to fetch. Defaults to the first page if not specified.
     * @return A paginated response containing a list of [LocationResponse] for the specified page.
     */
    suspend fun getLocations(page: Int? = null): PaginatedResponse<LocationResponse> = client
        .get("location/") {
            if (page != null) {
                url {
                    parameter("page", page)
                }
            }
        }
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches the details of a specific location using its ID.
     *
     * @param id The unique ID of the location to retrieve.
     * @return The [LocationResponse] containing details of the requested location.
     */
    suspend fun getLocation(id: Int): LocationResponse = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Retrieves multiple locations by their IDs in a single request.
     *
     * @param ids Comma-separated string of location IDs to retrieve.
     * @return A list of [LocationResponse] for the specified IDs.
     */
    suspend fun getLocationsFromIds(@MustBeCommaSeparatedIds ids: String): List<LocationResponse> {
        IdListValidator.assertValid(ids)

        return client
            .get("location/$ids")
            .accept(HttpStatusCode.OK)
            .body()
    }
}