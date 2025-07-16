package com.github.app.ui.repo.screen

import android.os.Parcelable
import androidx.annotation.StringRes
import com.github.app.core.viewmodel.AppViewState
import com.github.app.core.viewmodel.UiState
import com.github.app.ui.repo.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

data class TrendingRepositoriesScreenViewState(
    val currentState: UiState<Unit>,
    val repositories: ImmutableList<RepositoryViewState>,
    val filterButtons: ImmutableList<FilterButtonViewState>,
) : AppViewState {

    fun withRequestLoading(): TrendingRepositoriesScreenViewState {
        return copy(currentState = UiState.Loading)
    }

    fun withRepositories(repositories: List<RepositoryViewState>): TrendingRepositoriesScreenViewState {
        return copy(
            currentState = UiState.Success(Unit),
            repositories = repositories.toImmutableList(),
        )
    }

    fun withError(): TrendingRepositoriesScreenViewState {
        return copy(currentState = UiState.Error)
    }

    fun withFilterSelected(filterButtonViewState: FilterButtonViewState): TrendingRepositoriesScreenViewState {
        val filterButtons = filterButtons
            .map { it.copy(isSelected = it == filterButtonViewState) }
            .toImmutableList()

        return copy(filterButtons = filterButtons)
    }

    companion object {
        fun initialState() = TrendingRepositoriesScreenViewState(
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

@Parcelize
data class RepositoryViewState(
    val id: String,
    val name: String,
    val authorName: String,
    val imageUrl: String,
    val description: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val pullRequestsCount: Int,
    val discussionsCount: Int,
) : Parcelable
