package com.github.app

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import com.github.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initStrictMode()
        initKoin()
        initTimber()
    }

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDeathOnNetwork()
                    .penaltyLog()
                    .build(),
            )

            StrictMode.setVmPolicy(
                VmPolicy
                    .Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build(),
            )
        }
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@GithubApp)
            modules(appModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
