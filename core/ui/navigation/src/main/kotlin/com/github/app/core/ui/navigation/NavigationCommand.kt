package com.github.app.core.ui.navigation

/**
 * Represents a command for navigating within the application.
 *
 * This sealed class defines the different types of navigation actions that can be performed.
 */
sealed class NavigationCommand {
    data class Navigate(val route: String) : NavigationCommand()
    object Back : NavigationCommand()
}
