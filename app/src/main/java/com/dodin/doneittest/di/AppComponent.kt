package com.dodin.doneittest.di

import com.dodin.doneittest.MainActivity
import com.dodin.doneittest.data.remote.MovieApiService
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelsModule::class])
@Singleton
interface AppComponent {
    fun getMovieService(): MovieApiService

    fun inject(activity: MainActivity)
}