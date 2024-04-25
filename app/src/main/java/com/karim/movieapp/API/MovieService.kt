package com.karim.movieapp.API

import com.karim.movieapp.models.movieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieService{

    @Headers("Authorization: Bearer // Paste your API kry //")
    @GET("movie/popular")
    suspend fun getMoviesList(): movieModel


    companion object{
        var movieService: MovieService? = null
        fun getInstance(): MovieService {
            if (movieService == null){
                movieService = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MovieService::class.java)
            }
            return movieService!!
        }

    }



}