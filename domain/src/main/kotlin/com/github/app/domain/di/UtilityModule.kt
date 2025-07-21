package com.github.app.domain.di

import org.koin.dsl.module
import kotlin.time.Clock

internal val utilityModule = module {
    single<Clock> { Clock.System }
}
