package cz.crusty.aircrafter.base

import android.app.Application
import cz.crusty.aircrafter.BuildConfig
import cz.crusty.aircrafter.inject.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModules)
        }

        Timber.d("App Started !!!")
    }

}