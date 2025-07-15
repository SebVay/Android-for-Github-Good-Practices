package com.github.app.data.apollo.di

import com.github.app.data.apollo.repo.ApolloRepositoryDataSource
import com.github.app.data.apollo.repo.ApolloRepositoryDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::ApolloRepositoryDataSourceImpl) bind ApolloRepositoryDataSource::class
} + mapperModule
