package com.github.app.ui.repo.di

import com.github.app.ui.repo.mapper.RepositoriesUiMapper
import com.github.app.ui.repo.mapper.RepositoriesUiMapperImpl
import com.github.app.ui.repo.screen.RepositoriesScreenViewModel
import com.github.app.ui.repo.screen.TrendingRepositoriesScreenViewState
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiRepositoryModule = module {
    viewModelOf(::RepositoriesScreenViewModel)

    singleOf(TrendingRepositoriesScreenViewState::initialState)

    singleOf(::RepositoriesUiMapperImpl) bind RepositoriesUiMapper::class
}
