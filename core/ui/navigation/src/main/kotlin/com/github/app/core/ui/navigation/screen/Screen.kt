package com.github.app.core.ui.navigation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * Represents a screen in the application.
 *
 * Each screen has a unique [screenRoute] that is used for navigation.
 * The [Content] function, implemented by subclasses, defines how this screen is added to the navigation graph.
 *
 * @property screenRoute The unique route identifier for the screen.
 */
@Stable
abstract class Screen(val screenRoute: String) {

    @Composable
    abstract fun Content()
}
