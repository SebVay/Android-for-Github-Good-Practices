package com.github.app.ui.repo.screen

import androidx.lifecycle.viewModelScope
import com.github.app.core.viewmodel.AppViewModel
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.ui.repo.mapper.RepositoriesUiMapper
import kotlinx.coroutines.launch

internal class RepositoriesScreenViewModel(
    initialState: TrendingRepositoriesScreenViewState,
    private val getTrendingRepositories: GetTrendingRepositoriesUseCase,
    private val repositoriesMapper: RepositoriesUiMapper,
) : AppViewModel<TrendingRepositoriesScreenViewState>(initialState),
    RepositoriesScreenInteraction {

    init {
        launchRequest()
    }

    private fun launchRequest() {
        viewModelScope.launch {
            updateViewState { withRequestLoading() }

            val result = getTrendingRepositories()
                .map(repositoriesMapper)

            result.onSuccess { repositories ->
                updateViewState {
                    withRepositories(repositories)
                }
            }

            result.onFailure {
                updateViewState { withError() }
            }
        }
    }

    override fun onClickRepository(repositoryViewState: RepositoryViewState) {
        navigationController.navigateTo(RepositoriesScreen2)
    }
}
