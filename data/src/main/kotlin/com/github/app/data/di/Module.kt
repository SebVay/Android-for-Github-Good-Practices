package com.github.app.data.di

import com.github.app.data.contract.GetTrendingRepositoriesImpl
import com.github.app.domain.contract.GetTrendingRepositories
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::GetTrendingRepositoriesImpl) bind GetTrendingRepositories::class
}.apply {
    includes(dataSourceModule)
}
