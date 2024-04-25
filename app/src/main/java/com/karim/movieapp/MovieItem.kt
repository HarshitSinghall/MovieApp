package com.karim.movieapp


import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.karim.movieapp.models.Movie
import androidx.compose.runtime.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: Movie, pagerState: PagerState, index: Int) {

    Box(modifier = Modifier.fillMaxSize()) {

        AsyncImage(model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
                .blur(25.dp)
                .graphicsLayer {
                    val pageOffset = pagerState.calculateCurrentOffsetForPage(index)
                    // translate the contents by the size of the page, to prevent the pages from sliding in from left or right and stays in the center
                    translationX = pageOffset * size.width
                    // apply an alpha to fade the current page in and the old page out
                    alpha = 1 - pageOffset.absoluteValue
                }
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(65.dp)
                .pagerCubeInDepthTransition(index, pagerState),
            shape = RoundedCornerShape(25.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w185${movie.posterPath}",
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {


                    Text(
                        text = movie.title,
                        color = Color.Black,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp)
                    )

                    Text(
                        text = "Release - ${movie.releaseDate}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    Text(
                        text = "Description - ${movie.overview}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )


                    RatingBar(value = normalizePopularity(movie.popularity),
                        style = RatingBarStyle.Default,
                        onValueChange = {
                        },
                        modifier = Modifier.padding(10.dp)) {

                    }


                }

            }
        }
    }

}

fun normalizePopularity(popularity: Double): Float {
    // Define a reasonable upper limit for popularity (adjust as needed)
    val tmdbMaxPopularity = 1000.0

    // Define the range for your rating bar
    val ratingMin = 0.0
    val ratingMax = 5.0

    // Normalize the popularity value to the range [0, 1]
    val normalizedPopularity = popularity / tmdbMaxPopularity

    // Scale the normalized value to the range [ratingMin, ratingMax]
    val rating = normalizedPopularity * (ratingMax - ratingMin) + ratingMin

    return rating.toFloat()
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerCubeInDepthTransition(page: Int, pagerState: PagerState) = graphicsLayer {
    cameraDistance = 30f
    // Calculate the absolute offset for the current page from the
    // scroll position.
    val pageOffset = pagerState.calculateCurrentOffsetForPage(page)

    if (pageOffset < -1f) {
        // page is far off screen
        alpha = 0f
    } else if (pageOffset <= 0) {
        // page is to the right of the selected page or the selected page
        alpha = 1f
        transformOrigin = TransformOrigin(0f, 0.5f)
        rotationY = -90f * pageOffset.absoluteValue

    } else if (pageOffset <= 1) {
        // page is to the left of the selected page
        alpha = 1f
        transformOrigin = TransformOrigin(1f, 0.5f)
        rotationY = 90f * pageOffset.absoluteValue
    } else {
        alpha = 0f
    }

    if (pageOffset.absoluteValue <= 0.5) {
        scaleY = 0.4f.coerceAtLeast(1 - pageOffset.absoluteValue)
    } else if (pageOffset.absoluteValue <= 1) {
        scaleY = 0.4f.coerceAtLeast(1 - pageOffset.absoluteValue)
    }
}

// extension method for current page offset
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}