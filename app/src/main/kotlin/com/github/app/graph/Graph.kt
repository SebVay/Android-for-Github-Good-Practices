package com.github.app.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.app.core.ui.navigation.screen.Screen
import com.github.app.ui.repo.screen.RepositoriesScreen
import com.github.app.ui.repo.screen.placeholder.ScreenPlaceholder

/**
 * Composable function that defines the navigation graph of the application.
 *
 * It uses a [NavHost] to define the different screens and their corresponding routes.
 * The [NavHostController] is then used to navigate between screens.
 *
 * It closely follows the [Screen] defined in the `ui-navigation` module.
 */
@Composable
fun Graph() {
    val navController = rememberNavController()

    NavigationEvents(navController)

    NavHost(
        navController = navController,
        startDestination = RepositoriesScreen.screenRoute,
    ) {
        composable(RepositoriesScreen.screenRoute) {
            RepositoriesScreen.Content()
        }

        composable(ScreenPlaceholder.screenRoute) {
            ScreenPlaceholder.Content()
        }
    }
}
