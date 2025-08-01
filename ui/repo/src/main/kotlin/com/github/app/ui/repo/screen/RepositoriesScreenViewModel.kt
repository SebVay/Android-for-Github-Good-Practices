package com.github.app.ui.repo.screen

import com.github.app.core.ui.navigation.NavigationController
import com.github.app.core.viewmodel.AppViewModel
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.domain.repo.usecase.TimePeriod
import com.github.app.ui.repo.mapper.RepositoryViewStateMapper
import com.github.app.ui.repo.screen.placeholder.ScreenPlaceholder
import com.github.app.ui.repo.screen.state.FilterButtonViewState
import com.github.app.ui.repo.screen.state.FilterType
import com.github.app.ui.repo.screen.state.RepositoriesScreenViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach

internal class RepositoriesScreenViewModel(
    initialState: RepositoriesScreenViewState = RepositoriesScreenViewState.initialState(),
    private val navigationController: NavigationController,
    private val getTrendingRepositories: GetTrendingRepositoriesUseCase,
    private val repositoryMapper: RepositoryViewStateMapper,
) : AppViewModel<RepositoriesScreenViewState>(initialState),
    RepositoriesScreenInteraction {

    init {
        launchRequest()
    }

    private fun launchRequest() {
        viewStateFlow
            .map { it.filterButtons }
            .distinctUntilChanged()
            .onEach { updateViewState { withRequestLoading() } }
            .mapNotNull { filters -> filters.findSelectedFilter() }
            .map { selectedFilter -> getTrendingRepositories(selectedFilter.toDomain()) }
            .collectCatching { result ->
                result.onSuccess { repositories ->
                    updateViewState {
                        withRepositories(repositories.map { repository -> repositoryMapper(repository)})
                    }
                }

                result.onFailure {
                    updateViewState { withError() }
                }
            }
    }

    private fun ImmutableList<FilterButtonViewState>.findSelectedFilter(): FilterType? {
        return find(FilterButtonViewState::isSelected)?.filterType
    }

    override fun onClickRepository(repositoryViewState: RepositoryViewState) {
        navigationController.navigateTo(ScreenPlaceholder)
    }

    override fun onClickFilterButton(filterButtonViewState: FilterButtonViewState) {
        updateViewState {
            withFilterSelected(filterButtonViewState)
        }
    }
}

private fun FilterType?.toDomain() = when (this) {
    null, FilterType.YESTERDAY -> TimePeriod.YESTERDAY
    FilterType.LAST_WEEK -> TimePeriod.LAST_WEEK
    FilterType.LAST_MONTH -> TimePeriod.LAST_MONTH
}
