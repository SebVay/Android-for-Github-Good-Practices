package com.github.app.core.viewmodel

import androidx.lifecycle.ViewModel
import com.github.app.core.ui.navigation.NavigationController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.context.GlobalContext

open class AppViewModel<VS : AppViewState>(
    initialState: VS,
    val navigationController: NavigationController = getNavigationController(),
) : ViewModel() {

    private val updatableViewStateFlow: MutableStateFlow<VS> = MutableStateFlow(initialState)
    val viewStateFlow: StateFlow<VS> = updatableViewStateFlow.asStateFlow()

    /**
     * Updates the view state using the provided update block.
     * Observers of [viewStateFlow] will then be notified.
     *
     * @param updateBlock Lambda that takes the current view state as a receiver
     *                    and returns the updated view state.
     *                    The returned state will then be used to update the `viewStateFlow`.
     */
    protected fun updateViewState(updateBlock: VS.() -> VS) {
        updatableViewStateFlow.update { state -> updateBlock(state) }
    }
}

/**
 * Retrieves the [NavigationController] from Koin.
 *
 * This is mainly used because we want to ease the use for [AppViewModel] subclasses,
 * while letting the opportunity to be overridden and tested.
 *
 * @return The [NavigationController] instance that has been injected through Koin.
 */
private fun getNavigationController() = with(GlobalContext.get()) {
    get<NavigationController>()
}
