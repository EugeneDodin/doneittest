package com.dodin.doneittest.di

import android.content.Context
import com.dodin.doneittest.common.Schedulers
import com.dodin.doneittest.data.remote.MovieApiService
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val context: Context) {
    @Provides
    fun movieApiService(): MovieApiService {
        return MovieApiService()
    }

    @Provides
    fun schedulers(): Schedulers {
        return Schedulers()
    }

    @Provides
    fun context(): Context {
        return context
    }
}