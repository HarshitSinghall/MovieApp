package com.karim.movieapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karim.movieapp.API.MovieService
import com.karim.movieapp.models.Movie
import com.karim.movieapp.models.movieModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var movieList: MutableList<Movie> by mutableStateOf(mutableListOf())
    var errorMessage: String by mutableStateOf("")


    fun getMovieList(){
        viewModelScope.launch {
            val apiService = MovieService.getInstance()
            try{
                val movies = apiService.getMoviesList()
                movieList = mutableListOf() // Initialize movieList as an empty mutable lis
                movies.results.forEach { movie ->
                    movieList.add(movie)
                }
                Log.d("harshit", "getMovieList: $movieList")
            }
            catch(e: Exception){
                errorMessage = e.localizedMessage
            }
        }
    }



}