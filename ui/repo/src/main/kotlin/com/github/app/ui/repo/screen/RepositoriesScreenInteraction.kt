package com.github.app.ui.repo.screen

import com.github.app.ui.repo.screen.state.FilterButtonViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState

internal interface RepositoriesScreenInteraction {
    fun onClickRepository(repositoryViewState: RepositoryViewState)
    fun onClickFilterButton(filterButtonViewState: FilterButtonViewState)
}
