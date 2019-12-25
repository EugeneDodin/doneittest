package com.dodin.doneittest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PopularResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String,
    val title: String
)