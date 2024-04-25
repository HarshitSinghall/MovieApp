package com.karim.movieapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.karim.movieapp.models.Movie
import com.karim.movieapp.ui.theme.MovieAppTheme
import com.karim.movieapp.viewModels.MainViewModel

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {


    val movieViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var moviesList by remember { mutableStateOf<List<Movie>>(emptyList()) }

                    movieViewModel.getMovieList()
                    moviesList = movieViewModel.movieList

                    LaunchedEffect(Unit) {
                        movieViewModel.getMovieList()
                    }

                    MovieList(movieList = moviesList)

                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(movieList: List<Movie>){

    val pagerState = rememberPagerState{movieList.size}

    HorizontalPager(state = pagerState,
        modifier = Modifier.fillMaxSize()) {index ->
        MovieItem(movie = movieList[index], pagerState, index)

    }
    


}