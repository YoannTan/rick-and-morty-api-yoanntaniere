package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard
import org.mathieu.cleanrmapi.ui.core.composables.LocationDetailsHeader
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsAction
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsState

/**
 * Composable screen displaying details of a specific location.
 *
 * @param navController Navigation controller used for navigation actions.
 * @param id The ID of the location to display.
 */
@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(Unit) {
            viewModel.init(id)
        }

        Content(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

/**
 * Main content composable handling screen state and rendering UI accordingly.
 *
 * @param state The current UI state for the location detail.
 * @param onAction Callback for user actions.
 * @param onClickBack Callback for back navigation.
 */
@Composable
private fun Content(
    state: LocationDetailsState = LocationDetailsState.Loading,
    onAction: (LocationDetailsAction) -> Unit = { },
    onClickBack: () -> Unit = { }
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(),
    contentAlignment = Alignment.Center
) {
    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart)
            .zIndex(1f),
        onClick = onClickBack
    )

    Crossfade(targetState = state) {
        when (it) {
            is LocationDetailsState.Error -> ErrorView(error = it.message)
            is LocationDetailsState.Loaded -> LocationDetailsContent(
                state = it,
                onAction = onAction
            )
            LocationDetailsState.Loading -> {
                // TODO: Could display a loading animation here
            }
        }
    }
}

/**
 * Composable that displays an error message.
 *
 * @param error The error message to display.
 */
@Composable
private fun ErrorView(error: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = error,
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    )
}

/**
 * Object that encapsulates the UI rendering of the loaded location state.
 */
private object LocationDetailsContent {

    /**
     * Main UI content when the location is successfully loaded.
     *
     * @param state The loaded state containing the location.
     * @param onAction Callback for user actions.
     */
    @Composable
    operator fun invoke(
        state: LocationDetailsState.Loaded,
        onAction: (LocationDetailsAction) -> Unit
    ) {
        val location = state.location

        var offsetY by remember {
            mutableFloatStateOf(0f)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(
                state = state,
                offsetY = offsetY,
                onAction = onAction,
                location = location
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Residents of this location",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 4.dp)
                        .background(PrimaryColor.copy(alpha = 0.2f))
                )
            }

            LazyVerticalGrid(
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(location.residents.size) { index ->
                    val character = location.residents[index]
                    CharacterCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onAction(LocationDetailsAction.SelectedCharacter(character.id))
                            },
                        character = character
                    )
                }
            }
        }
    }

    /**
     * Header composable displaying location metadata.
     *
     * @param state The current loaded state.
     * @param offsetY The vertical offset for potential animation (currently unused).
     * @param onAction Callback for actions.
     * @param location The location being displayed.
     */
    @Composable
    private fun Header(
        state: LocationDetailsState.Loaded,
        offsetY: Float,
        onAction: (LocationDetailsAction) -> Unit,
        location: org.mathieu.cleanrmapi.domain.location.models.Location
    ) {
        LocationDetailsHeader(
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            residentsCount = location.residents.size
        )
    }
}
