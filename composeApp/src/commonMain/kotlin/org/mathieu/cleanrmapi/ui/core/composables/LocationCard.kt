package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mathieu.cleanrmapi.common.useClickSound
import org.mathieu.cleanrmapi.ui.core.theme.OnBackgroundColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

/**
 * A composable representing a styled location card with an icon and a text.
 *
 * This card reacts to clicks by triggering a sound (via [useClickSound]) and a provided [onClick] action.
 *
 * @param modifier Modifier applied to the root element.
 * @param orientation Layout orientation (vertical or horizontal).
 * @param imageVector The icon displayed alongside the text.
 * @param location The name or label to display inside the card.
 * @param backgroundColor Background color of the card.
 * @param color Tint color for icon and text.
 * @param onClick Callback triggered on card click.
 */
@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    imageVector: ImageVector,
    location: String,
    backgroundColor: Color = SurfaceColor,
    color: Color = OnBackgroundColor,
    onClick: () -> Unit = {}
) {
    val playSound = useClickSound()

    val clickableModifier = modifier.clickable {
        playSound()
        onClick()
    }

    when (orientation) {
        Orientation.Vertical ->
            Column(
                modifier = clickableModifier
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Content(color, imageVector, location)
            }

        Orientation.Horizontal ->
            Row(
                modifier = clickableModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Content(color, imageVector, location)
            }
    }
}

/**
 * Internal content block of the location card that shows the icon and text.
 *
 * @param color Tint color applied to the image and text.
 * @param imageVector The icon to display.
 * @param text The string to display under or beside the icon.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    color: Color,
    imageVector: ImageVector,
    text: String
) {
    Image(
        imageVector = imageVector,
        contentDescription = null,
        colorFilter = ColorFilter.tint(color)
    )

    Text(
        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
        text = text,
        color = color,
        fontSize = 14.sp
    )
}
