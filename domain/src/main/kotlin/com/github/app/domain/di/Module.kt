package com.github.app.domain.di

import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCase
import com.github.app.domain.repo.usecase.GetTrendingRepositoriesUseCaseImpl
import com.github.app.domain.repo.usecase.mapper.TrendingRepositoryMapper
import com.github.app.domain.repo.usecase.mapper.TrendingRepositoryMapperImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModules = module {
    singleOf(::TrendingRepositoryMapperImpl) bind TrendingRepositoryMapper::class
    singleOf(::GetTrendingRepositoriesUseCaseImpl) bind GetTrendingRepositoriesUseCase::class
}
