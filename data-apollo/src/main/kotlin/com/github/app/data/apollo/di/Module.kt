package com.github.app.data.apollo.di

val apolloModules = buildList {
    add(apolloClientModule)
    addAll(dataSourceModule)
}
