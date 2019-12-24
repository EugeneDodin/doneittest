package com.dodin.doneittest.data.remote

import com.dodin.doneittest.data.remote.dto.PopularResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/popular")
    fun getPopular(): Observable<PopularResponse>
}