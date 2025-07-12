package com.github.app.data.di

import com.github.app.data.RepoDataSource
import com.github.app.data.RepoDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::RepoDataSourceImpl) bind RepoDataSource::class
}
