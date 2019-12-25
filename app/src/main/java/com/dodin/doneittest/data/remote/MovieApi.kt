package com.dodin.doneittest.data.remote

import com.dodin.doneittest.data.remote.dto.PopularResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int): Single<PopularResponse>
}