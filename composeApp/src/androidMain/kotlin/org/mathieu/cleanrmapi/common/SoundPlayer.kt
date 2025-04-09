package org.mathieu.cleanrmapi.common

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.mathieu.cleanrmapi.R

/**
 * Actual implementation of [useClickSound] for the Android platform.
 *
 * This function provides a lambda that, when invoked, plays a click sound
 * (stored in the raw resources as `portal_click_sound.mp3`) using [MediaPlayer].
 *
 * It is intended to be used inside a @Composable context to access the current [LocalContext].
 *
 * @return A lambda function that plays the click sound when called.
 */
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