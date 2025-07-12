package com.github.app.data.di

import com.github.app.data.apollo.di.apolloModules

val dataModules = buildList {
    // Internal Modules
    add(dataSourceModule)
    add(mapperModule)
    // External Modules
    addAll(apolloModules)
}
