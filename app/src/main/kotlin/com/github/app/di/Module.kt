package com.github.app.di

import com.github.app.core.ui.navigation.di.navigationModule
import com.github.app.data.di.dataModules
import com.github.app.domain.di.domainModules
import com.github.app.ui.repo.di.uiRepositoryModule
import org.koin.core.module.Module

val appModule = buildList<Module> {
    addAll(dataModules)
    addAll(domainModules)
    add(navigationModule)
    add(uiRepositoryModule)
}
