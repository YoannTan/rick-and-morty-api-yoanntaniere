package org.mathieu.cleanrmapi.common

import androidx.compose.runtime.Composable

/**
 * Provides a platform-specific lambda that plays a click sound.
 *
 * This function is declared as `expect` to be implemented separately on each target platform.
 * It is intended to be called from within a @Composable context and returns a lambda
 * that, when invoked, triggers a sound effect (typically on user interaction like a click).
 *
 * @return A lambda function to play a click sound.
 */
@Composable
expect fun useClickSound(): () -> Unit