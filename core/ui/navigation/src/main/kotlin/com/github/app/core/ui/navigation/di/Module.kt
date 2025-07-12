package com.github.app.core.ui.navigation.di

import com.github.app.core.ui.navigation.NavigationController
import com.github.app.core.ui.navigation.NavigationControllerImpl
import org.koin.dsl.module

val navigationModule = module {
    single<NavigationController> { NavigationControllerImpl() }
}
