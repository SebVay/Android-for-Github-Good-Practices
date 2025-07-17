package com.github.app.ui.repo.di

import com.github.app.ui.repo.mapper.RepositoryViewStateMapper
import com.github.app.ui.repo.mapper.RepositoryViewStateMapperImpl
import com.github.app.ui.repo.screen.RepositoriesScreenViewModel
import com.github.app.ui.repo.screen.state.RepositoriesScreenViewState
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiRepositoryModule = module {
    viewModelOf(::RepositoriesScreenViewModel)

    singleOf(RepositoriesScreenViewState::initialState)

    singleOf(::RepositoryViewStateMapperImpl) bind RepositoryViewStateMapper::class
}
