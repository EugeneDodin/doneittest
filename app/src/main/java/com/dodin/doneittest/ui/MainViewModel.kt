package com.dodin.doneittest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dodin.doneittest.common.Schedulers
import com.dodin.doneittest.data.remote.MovieApiService
import com.dodin.doneittest.data.remote.dto.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: MovieApiService) : ViewModel() {
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<List<Movie>>()

    init {
        disposable.add(
            api.getPopular()
                .subscribeOn(Schedulers.network())
                .observeOn(Schedulers.ui())
                .subscribe(
                    {
                        val previous = movies.value ?: emptyList()
                        val total = previous + it.results
                        movies.postValue(total)
                    },
                    {

                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}