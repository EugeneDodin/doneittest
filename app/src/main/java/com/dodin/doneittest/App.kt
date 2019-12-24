package com.dodin.doneittest

import android.app.Application
import com.dodin.doneittest.di.AppComponent
import com.dodin.doneittest.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
         DaggerAppComponent.builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()

    }
}