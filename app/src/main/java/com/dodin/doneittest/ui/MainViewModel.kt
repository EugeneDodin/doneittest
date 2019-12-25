package com.dodin.doneittest.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dodin.doneittest.common.Schedulers
import com.dodin.doneittest.data.remote.MovieApiService
import com.dodin.doneittest.data.remote.dto.Movie
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val api: MovieApiService,
    private val context: Context,
    private val schedulers: Schedulers
) : ViewModel() {

    val movies = MutableLiveData<List<Movie>>()
    val networkState = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()
    private var hasMorePages = true
    private var isLoading = false
    private var currentPage = 0

    private var moviesList: List<Movie> = emptyList()

    init {
        loadNext()
    }

    fun loadNext() {
        if (!hasMorePages || isLoading) {
            return
        }
        isLoading = true

        val subscription = ReactiveNetwork.observeNetworkConnectivity(context)
            .flatMapSingle {
                networkState.postValue(it.available())

                if (it.available()) {
                    val page = currentPage + 1
                    popularSingleForPage(page)
                } else {
                    Single.just(emptyList())
                }
            }
            .map { newMovies ->
                moviesList = moviesList + newMovies
                moviesList
            }
            .subscribeOn(schedulers.network())
            .observeOn(schedulers.ui())
            .subscribe(
                {
                    movies.postValue(it)
                },
                {

                }
            )
        disposable.add(subscription)
    }

    private fun popularSingleForPage(page: Int): Single<List<Movie>> {
        return api.getPopular(page)
            .doOnSuccess { response ->
                isLoading = false
                currentPage = response.page
                hasMorePages = currentPage < response.totalPages
            }
            .map { response ->
                response.results
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}