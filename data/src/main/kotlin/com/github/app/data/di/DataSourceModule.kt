package com.github.app.data.di

import com.github.app.data.RepositoriesDataSource
import com.github.app.data.RepositoriesDataSourceImpl
import com.github.app.data.apollo.di.apolloModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::RepositoriesDataSourceImpl) bind RepositoriesDataSource::class
}.apply {
    includes(apolloModule)
}
