package com.dodin.doneittest.data.remote

import com.dodin.doneittest.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiService() {
    private val api: MovieApi

    init {
        val gson = Gson()
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            httpClient.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        // Content type interceptor
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Accept", "application/json; utf-8")
                .header("Content-Type", "application/json")
                .method(original.method, original.body)
                .build()

            return@addInterceptor chain.proceed(request)
        }

        val baseUrl = BuildConfig.HOST
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create<MovieApi>(MovieApi::class.java)
    }

    fun getPopular() = api.getPopular()
}