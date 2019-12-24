package com.dodin.doneittest.di

import com.dodin.doneittest.data.remote.MovieApiService
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMovieService(): MovieApiService
}