package com.arctouch.io.outdoorsychallenge

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.arctouch.io.outdoorsychallenge.data.dataModule
import com.arctouch.io.outdoorsychallenge.domain.domainModule
import com.arctouch.io.outdoorsychallenge.features.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class OutdoorsyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin()

        if (BuildConfig.DEBUG) startLogging()
    }

    private fun startKoin() = startKoin {
        androidLogger(Level.DEBUG)
        androidContext(this@OutdoorsyApplication)
        modules(dataModule + domainModule + featuresModule)
    }

    private fun startLogging() = Timber.plant(Timber.DebugTree())
}
