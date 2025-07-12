package com.github.app.data.apollo.di

import com.github.app.data.apollo.repo.mapper.TrendingRepoMapper
import com.github.app.data.apollo.repo.mapper.TrendingRepoMapperImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val mapperModule = module {
    singleOf(::TrendingRepoMapperImpl) bind TrendingRepoMapper::class
}
