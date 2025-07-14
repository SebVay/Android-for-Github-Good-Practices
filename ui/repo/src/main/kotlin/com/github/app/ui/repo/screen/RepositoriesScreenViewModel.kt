package com.github.app.ui.repo.screen

import com.github.app.core.viewmodel.AppViewModel
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.ui.repo.mapper.RepositoriesUiMapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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
        viewStateFlow
            .map { it.filterButtons }
            .distinctUntilChanged()
            .onEach { updateViewState { withRequestLoading() } }
            .map { filters -> filters.find(FilterButtonViewState::isSelected) }
            .map { filter -> getTrendingRepositories().map(repositoriesMapper) }
            .collectCatching { result ->
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

    override fun onClickFilterButton(filterButtonViewState: FilterButtonViewState) {
        updateViewState {
            withFilterSelected(filterButtonViewState)
        }
    }
}
