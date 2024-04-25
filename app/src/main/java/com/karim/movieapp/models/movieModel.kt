package com.karim.movieapp.models

import com.google.gson.annotations.SerializedName

class movieModel(
    @SerializedName("results")
    val results: List<Movie>
)

data class Movie(
    val adult: Boolean,
    val id: Long,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val overview: String
)

