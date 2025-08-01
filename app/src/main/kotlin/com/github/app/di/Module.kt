package com.github.app.di

import com.github.app.core.ui.navigation.di.navigationModule
import com.github.app.data.di.dataModule
import com.github.app.domain.di.domainModule
import com.github.app.ui.repo.di.uiRepositoryModule
import org.koin.core.module.Module

val appModule: List<Module> = buildList {
    add(navigationModule)
    addAll(buildUiModules())
}

/**
 * Builds and returns a list of Koin modules for the UI layer.
 *
 * For each module in that list, it includes the `navigationModule` and the domain module.
 *
 * @return A list of Koin modules configured for the UI layer.
 */
private fun buildUiModules(): List<Module> {
    return listOf(uiRepositoryModule).applyForEach {
        includes(
            navigationModule,
            buildDomainModule()
        )
    }
}

private fun buildDomainModule() = domainModule.apply { includes(dataModule) }

private fun <T> List<T>.applyForEach(block: T.() -> Unit): List<T> = apply {
    forEach { it.block() }
}