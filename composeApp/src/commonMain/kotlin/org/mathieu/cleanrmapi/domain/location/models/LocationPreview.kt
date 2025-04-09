package org.mathieu.cleanrmapi.domain.location.models

/**
 * Represents a simplified version of a location,
 * typically used when only basic information is needed (e.g., in character details).
 *
 * @property id The internal unique identifier of the preview object.
 * @property name The display name of the location.
 * @property locationId The actual location ID corresponding to the full [Location] entity.
 */
data class LocationPreview (
    val id: Int,
    val name: String,
    val locationId: Int
)