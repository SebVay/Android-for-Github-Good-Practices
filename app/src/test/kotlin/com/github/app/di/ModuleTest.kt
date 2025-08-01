package com.github.app.di

import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verifyAll

class ModuleTest {
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        appModule.verifyAll()
    }
}