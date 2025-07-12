package com.github.app.data.apollo.di

import com.github.app.data.apollo.repo.TrendingRepoDataSource
import com.github.app.data.apollo.repo.TrendingRepoDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::TrendingRepoDataSourceImpl) bind TrendingRepoDataSource::class
} + mapperModule
