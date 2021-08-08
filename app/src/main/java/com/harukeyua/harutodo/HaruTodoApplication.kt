package com.harukeyua.harutodo

import android.app.Application
import com.harukeyua.harutodo.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HaruTodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HaruTodoApplication)
            modules(mainModule)
        }
    }
}