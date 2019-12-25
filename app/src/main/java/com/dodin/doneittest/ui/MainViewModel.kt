package com.dodin.doneittest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dodin.doneittest.common.Lce
import com.dodin.doneittest.common.Schedulers
import com.dodin.doneittest.data.remote.MovieApiService
import com.dodin.doneittest.data.remote.dto.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val api: MovieApiService) : ViewModel() {
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<Lce<List<Movie>>>()
    private val moviesList: MutableList<Movie> = mutableListOf()

    init {
        disposable.add(
            api.getPopular()
                .subscribeOn(Schedulers.network())
                .observeOn(Schedulers.ui())
                .doOnSubscribe { movies.postValue(Lce.Loading) }
                .subscribe(
                    {
                        moviesList.addAll(it.results)
                        movies.postValue(Lce.Success(moviesList))
                    },
                    {
                        movies.postValue(Lce.Error(it.localizedMessage ?: ""))
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}