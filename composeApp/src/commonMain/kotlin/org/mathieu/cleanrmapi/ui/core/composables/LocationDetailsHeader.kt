package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays a styled header for a location, including its name, type,
 * dimension, and number of residents. The content is presented in cards.
 *
 * @param name The name of the location.
 * @param type The type or category of the location.
 * @param dimension The dimension or universe the location belongs to.
 * @param residentsCount The number of characters residing in this location.
 */
@Composable
fun LocationDetailsHeader(
    name: String,
    type: String,
    dimension: String,
    residentsCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoCard(title = "Type", value = type)
            InfoCard(title = "Dimension", value = dimension)
            InfoCard(title = "Residents", value = "$residentsCount")
        }
    }
}

/**
 * Displays a single info card with a title and value.
 *
 * @param title The label describing the data (e.g., "Type").
 * @param value The value associated with the title (e.g., "Planet").
 */
@Composable
private fun InfoCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .defaultMinSize(minWidth = 90.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 16.sp)
        }
    }
}