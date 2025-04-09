package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    location: String,
    onClick: () -> Unit = {}
) {
    IconWithImage(
        modifier = modifier.clickable(onClick = onClick),
        imageVector = imageVector,
        text = location
    )
}