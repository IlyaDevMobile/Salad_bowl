package com.ilyakoz.saladbowl.cleancode.application.di.app

import android.app.Application
import com.ilyakoz.saladbowl.cleancode.application.di.appModule
import com.ilyakoz.saladbowl.cleancode.application.di.dataModule
import com.ilyakoz.saladbowl.cleancode.application.di.databaseModule
import com.ilyakoz.saladbowl.cleancode.application.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(databaseModule,appModule, domainModule,dataModule))
        }
    }

}