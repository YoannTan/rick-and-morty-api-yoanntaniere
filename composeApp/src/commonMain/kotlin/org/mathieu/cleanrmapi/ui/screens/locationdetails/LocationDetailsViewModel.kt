package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface LocationDetailsAction {
    data class SelectedCharacter(val characterId: Int) : LocationDetailsAction
}

class LocationDetailsViewModel :
    ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    private val locationRepository: LocationRepository by inject()

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

    fun handleAction(action: LocationDetailsAction) {
        when (action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.characterId.toString()))
        }
    }
}

sealed interface LocationDetailsState {
    object Loading : LocationDetailsState

    data class Error(val message: String) : LocationDetailsState

    data class Loaded(val location: Location) : LocationDetailsState
}