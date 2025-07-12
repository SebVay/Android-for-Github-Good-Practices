package com.github.app.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.app.core.ui.navigation.screen.Screen
import com.github.app.ui.repo.screen.RepositoriesScreen
import com.github.app.ui.repo.screen.RepositoriesScreen2

/**
 * Composable function that defines the navigation graph of the application.
 *
 * It uses a [NavHost] to define the different screens and their corresponding routes.
 * The [navController] is then used to navigate between screens.
 *
 * It closely follows the [Screen] defined in the `ui-navigation` module.
 *
 * @param navController The [NavHostController] used for navigation.
 */
@Composable
fun Graph(
    navController: NavHostController = rememberNavController(),
) {
    NavigationEvents(navController)

    NavHost(
        navController = navController,
        startDestination = RepositoriesScreen.screenRoute,
    ) {
        composable(RepositoriesScreen.screenRoute) {
            RepositoriesScreen.Content()
        }

        composable(RepositoriesScreen2.screenRoute) {
            RepositoriesScreen2.Content()
        }
    }
}
