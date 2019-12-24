package com.dodin.doneittest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dodin.doneittest.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [AppModule::class])
interface ViewModelsModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(viewModel: MainViewModel): ViewModel
}