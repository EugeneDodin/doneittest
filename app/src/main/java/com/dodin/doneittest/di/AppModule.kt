package com.dodin.doneittest.di

import com.dodin.doneittest.data.remote.MovieApiService
import dagger.Module
import dagger.Provides


@Module
class AppModule {
    @Provides
    fun movieApiService(): MovieApiService {
        return MovieApiService()
    }
}