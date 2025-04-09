package org.mathieu.cleanrmapi.common


import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.mathieu.cleanrmapi.R

@Composable
actual fun useClickSound(): () -> Unit {
    val context = LocalContext.current
    return {
        MediaPlayer.create(context, R.raw.portal_click_sound).apply {
            setOnCompletionListener { release() }
            start()
        }
    }
}