package com.github.app.core.ui.navigation

import com.github.app.core.ui.navigation.screen.Screen
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Interface for dispatching navigation commands.
 * This interface defines the contract for sending navigation events,
 * such as navigating to a specific screen or going back to the previous one.
 * It also provides a [SharedFlow] to observe these navigation commands.
 */
interface NavigationController {
    /**
     * Navigates to the specified screen.
     * This function emits a [NavigationCommand.Navigate] event to the [events] flow.
     *
     * @param screen The [Screen] to navigate to.
     */
    fun navigateTo(screen: Screen)

    /**
     * Dispatches a command to navigate back to the previous screen.
     * This function emits a [NavigationCommand.Back] event to the [events] flow.
     */
    fun navigateBack()

    /**
     * A [SharedFlow] that emits [NavigationCommand] events.
     * Observers can collect this flow to react to navigation requests.
     */
    val events: SharedFlow<NavigationCommand>
}

internal class NavigationControllerImpl(
    private val coroutineScope: CoroutineScope = defaultScope,
) : NavigationController {
    private val _events = MutableSharedFlow<NavigationCommand>()

    override val events = _events.asSharedFlow()

    override fun navigateTo(screen: Screen) {
        coroutineScope.launch {
            _events.emit(NavigationCommand.Navigate(screen.screenRoute))
        }
    }

    override fun navigateBack() {
        coroutineScope.launch {
            _events.emit(NavigationCommand.Back)
        }
    }

    companion object {
        private const val COROUTINE_NAME = "NavigationControllerCoroutine"
        private val defaultScope: CoroutineScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Default + CoroutineName(COROUTINE_NAME),
        )
    }
}
