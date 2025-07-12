package com.github.app.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.github.app.core.ui.navigation.NavigationCommand
import com.github.app.core.ui.navigation.NavigationController
import org.koin.compose.koinInject

/**
 * Composable function that observes navigation events from a [NavigationController]
 * and executes them on the provided [NavHostController].
 *
 * This function uses a [LaunchedEffect] to collect events from the [NavigationController]'s
 * event flow.
 * When a [NavigationCommand.Navigate] event is received, it navigates
 * to the specified route using the [navController].
 * When a [NavigationCommand.Back]
 * event is received, it pops the back stack of the [navController].
 *
 * @param navController The [NavHostController] to use for navigation.
 * @param navigationDispatcher The [NavigationController] to observe for navigation events.
 * By default, it's injected using Koin.
 */
@Composable
fun NavigationEvents(
    navController: NavHostController,
    navigationDispatcher: NavigationController = koinInject<NavigationController>(),
) {
    LaunchedEffect(Unit) {
        navigationDispatcher.events.collect { event ->
            when (event) {
                is NavigationCommand.Navigate -> navController.navigate(event.route)
                NavigationCommand.Back -> navController.popBackStack()
            }
        }
    }
}
