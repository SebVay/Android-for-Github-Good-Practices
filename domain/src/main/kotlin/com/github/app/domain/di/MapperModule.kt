package com.github.app.domain.di

import com.github.app.domain.repo.mapper.LanguageMapper
import com.github.app.domain.repo.mapper.LanguageMapperImpl
import com.github.app.domain.repo.mapper.TrendingRepositoryMapper
import com.github.app.domain.repo.mapper.TrendingRepositoryMapperImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val mapperModule = module {
    singleOf(::TrendingRepositoryMapperImpl) bind TrendingRepositoryMapper::class
    singleOf(::LanguageMapperImpl) bind LanguageMapper::class
}