package com.github.app.ui.repo.screen

import com.github.app.core.viewmodel.AppViewState
import com.github.app.core.viewmodel.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class TrendingRepositoriesScreenViewState(
    val repositoriesUiState: UiState<ImmutableList<RepositoryViewState>>,
) : AppViewState {

    fun withRequestLoading(): TrendingRepositoriesScreenViewState {
        return copy(repositoriesUiState = UiState.Loading)
    }

    fun withRepositories(repositoryList: List<RepositoryViewState>): TrendingRepositoriesScreenViewState {
        return copy(repositoriesUiState = UiState.Success(repositoryList.toImmutableList()))
    }

    fun withError(): TrendingRepositoriesScreenViewState {
        return copy(repositoriesUiState = UiState.Error)
    }

    companion object {
        fun initialState() = TrendingRepositoriesScreenViewState(
            repositoriesUiState = UiState.Loading,
        )
    }
}

data class RepositoryViewState(
    val repositoryId: String,
    val repositoryName: String,
    val authorName: String,
    val imageUrl: String,
    val description: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val pullRequestsCount: Int,
)
