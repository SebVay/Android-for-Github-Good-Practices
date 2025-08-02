package com.github.app.data.apollo.di

import com.github.app.data.apollo.repo.mapper.RepositoryMapper
import com.github.app.data.apollo.repo.mapper.RepositoryMapperImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val mapperModule: Module = module {
    singleOf(::RepositoryMapperImpl) bind RepositoryMapper::class
}
