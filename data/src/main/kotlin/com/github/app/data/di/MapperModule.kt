package com.github.app.data.di

import com.github.app.data.mapper.GetTrendingRepositoriesImpl
import com.github.app.domain.contract.GetTrendingRepositories
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val mapperModule = module {
    singleOf(::GetTrendingRepositoriesImpl) bind GetTrendingRepositories::class
}
