package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

/**
 * Represents the possible user actions on the Location Details screen.
 */
sealed interface LocationDetailsAction {
    /**
     * Triggered when a user selects a character from the resident list.
     *
     * @property characterId The ID of the selected character.
     */
    data class SelectedCharacter(val characterId: Int) : LocationDetailsAction
}

/**
 * ViewModel responsible for managing the state and logic of the Location Details screen.
 *
 * It handles data fetching from the repository, error states, and navigation events.
 */
class LocationDetailsViewModel :
    ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    private val locationRepository: LocationRepository by inject()

    /**
     * Initializes the screen by fetching location data from the repository.
     *
     * @param id The ID of the location to retrieve.
     */
    fun init(id: Int) {
        fetchData(
            source = { locationRepository.getLocation(id) }
        ) {
            onSuccess { location ->
                updateState { LocationDetailsState.Loaded(location) }
            }

            onFailure {
                updateState { LocationDetailsState.Error(it.message ?: "Unknown error") }
            }
        }
    }

    /**
     * Handles UI actions emitted by the view.
     *
     * @param action The [LocationDetailsAction] triggered by user interaction.
     */
    fun handleAction(action: LocationDetailsAction) {
        when (action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.characterId.toString()))
        }
    }
}

/**
 * Represents the UI state of the Location Details screen.
 */
sealed interface LocationDetailsState {

    /**
     * Indicates the screen is currently loading data.
     */
    object Loading : LocationDetailsState

    /**
     * Indicates that an error occurred during data retrieval.
     *
     * @property message A human-readable error message.
     */
    data class Error(val message: String) : LocationDetailsState

    /**
     * Represents a successfully loaded location.
     *
     * @property location The full [Location] data to display.
     */
    data class Loaded(val location: Location) : LocationDetailsState
}