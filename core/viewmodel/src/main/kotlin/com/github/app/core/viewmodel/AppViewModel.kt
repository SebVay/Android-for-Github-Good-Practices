package com.github.app.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

open class AppViewModel<VS : AppViewState>(
    initialState: VS,
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

    /**
     * Collects the given flow of [Result] in the `viewModelScope`.
     * This function also catches any exceptions thrown by the upstream flow and emits them as `Result.failure`.
     * This is a convenience function to avoid writing `viewModelScope.launch` and `catch` every time
     * a flow of [Result] needs to be collected in a ViewModel.
     *
     * @param collector The [FlowCollector] that will collect the flow.
     */
    protected fun <T> Flow<Result<T>>.collectCatching(collector: FlowCollector<Result<T>>) {
        viewModelScope.launch {
            this@collectCatching
                .catch { throwable ->
                    Timber.e(throwable, "An error occurred while collecting the flow.")
                    emit(Result.failure(throwable))
                }
                .collect(collector)
        }
    }
}
