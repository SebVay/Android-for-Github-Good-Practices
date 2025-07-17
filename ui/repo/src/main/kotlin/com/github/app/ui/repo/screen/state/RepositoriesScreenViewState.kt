package com.github.app.ui.repo.screen.state

import androidx.annotation.StringRes
import com.github.app.core.viewmodel.AppViewState
import com.github.app.core.viewmodel.UiState
import com.github.app.ui.repo.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal data class RepositoriesScreenViewState(
    val currentState: UiState<Unit>,
    val repositories: ImmutableList<RepositoryViewState>,
    val filterButtons: ImmutableList<FilterButtonViewState>,
) : AppViewState {

    fun withRequestLoading(): RepositoriesScreenViewState {
        return copy(currentState = UiState.Loading)
    }

    fun withRepositories(repositories: List<RepositoryViewState>): RepositoriesScreenViewState {
        return copy(
            currentState = UiState.Success(Unit),
            repositories = repositories.toImmutableList(),
        )
    }

    fun withError(): RepositoriesScreenViewState {
        return copy(currentState = UiState.Error)
    }

    fun withFilterSelected(filterButtonViewState: FilterButtonViewState): RepositoriesScreenViewState {
        val filterButtons = filterButtons
            .map { it.copy(isSelected = it == filterButtonViewState) }
            .toImmutableList()

        return copy(filterButtons = filterButtons)
    }

    companion object {
        fun initialState() = RepositoriesScreenViewState(
            currentState = UiState.Loading,
            repositories = persistentListOf(),
            filterButtons = persistentListOf(
                FilterButtonViewState(
                    filterType = FilterType.YESTERDAY,
                    textRes = R.string.filter_button_last_yesterday,
                    isSelected = true,
                ),
                FilterButtonViewState(
                    filterType = FilterType.LAST_WEEK,
                    textRes = R.string.filter_button_last_week,
                    isSelected = false,
                ),
                FilterButtonViewState(
                    filterType = FilterType.LAST_MONTH,
                    textRes = R.string.filter_button_last_month,
                    isSelected = false,
                ),
            ),
        )
    }
}

data class FilterButtonViewState(
    val filterType: FilterType,
    @param:StringRes val textRes: Int,
    val isSelected: Boolean,
)

enum class FilterType {
    YESTERDAY,
    LAST_WEEK,
    LAST_MONTH,
}
