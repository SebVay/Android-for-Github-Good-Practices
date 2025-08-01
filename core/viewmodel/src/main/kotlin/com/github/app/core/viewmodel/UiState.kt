package com.github.app.core.viewmodel

import androidx.compose.runtime.Stable
import com.github.app.core.viewmodel.UiState.Success

/**
 * Represents the different states of a UI that interacts with data.
 *
 * This sealed class is designed to be used in ViewModels to expose the current state
 * of data loading, success, emptiness, or error to the UI.
 *
 * It is marked as `@Stable` to indicate to the Compose compiler that its instances
 * are immutable once created, allowing for optimized recompositions.
 *
 * @param T The type of data this UI state holds in its `Success` state.
 */
@Stable
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()

    /**
     * Represents a state where the data is empty or not found.
     * This is useful for scenarios like an empty list or a search returning no results.
     */
    data object Empty : UiState<Nothing>()

    data object Error : UiState<Nothing>()
}
