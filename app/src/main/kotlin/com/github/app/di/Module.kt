package com.github.app.di

import com.github.app.core.ui.navigation.di.navigationModule
import com.github.app.data.di.dataModules
import com.github.app.domain.di.domainModules
import com.github.app.ui.repo.di.uiRepositoryModule

val appModule = buildList {
    addAll(dataModules)
    add(domainModules)
    add(navigationModule)
    add(uiRepositoryModule)
}
