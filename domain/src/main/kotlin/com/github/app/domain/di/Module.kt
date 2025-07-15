package com.github.app.domain.di

import com.github.app.domain.repo.mapper.TrendingRepositoryMapper
import com.github.app.domain.repo.mapper.TrendingRepositoryMapperImpl
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.time.Clock

val domainModules = module {
    singleOf(::TrendingRepositoryMapperImpl) bind TrendingRepositoryMapper::class
    singleOf(::GetTrendingRepositoriesUseCaseImpl) bind GetTrendingRepositoriesUseCase::class

    single<Clock> { Clock.System }
}
