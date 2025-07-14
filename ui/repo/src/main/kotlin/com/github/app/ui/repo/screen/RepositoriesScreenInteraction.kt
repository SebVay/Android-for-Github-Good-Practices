package com.github.app.ui.repo.screen

internal interface RepositoriesScreenInteraction {
    fun onClickRepository(repositoryViewState: RepositoryViewState)
    fun onClickFilterButton(filterButtonViewState: FilterButtonViewState)
}
