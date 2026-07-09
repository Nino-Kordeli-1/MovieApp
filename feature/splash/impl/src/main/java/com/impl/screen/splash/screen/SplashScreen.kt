package com.impl.screen.splash.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.common.resource.NetworkResult
import com.impl.screen.splash.vm.SplashViewModel
import com.navigation.Navigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navigator: Navigator
) {
    val viewModel: SplashViewModel = koinViewModel()

    val moviesState by viewModel.movies.collectAsState()
    val genreState by viewModel.genre.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when (moviesState) {

            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResult.Error -> {
                Text(
                    text = (moviesState as NetworkResult.Error).errorMessage
                )
            }

            is NetworkResult.Success -> {

                val movies =
                    (moviesState as NetworkResult.Success).data

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        Text(
                            text = "GENRES",
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    when (genreState) {

                        is NetworkResult.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        is NetworkResult.Error -> {
                            item {
                                Text(
                                    text = (genreState as NetworkResult.Error).errorMessage,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        is NetworkResult.Success -> {

                            val genres =
                                (genreState as NetworkResult.Success).data

                            items(genres) { genre ->
                                Text(
                                    text = genre.name,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }

                    item {
                        HorizontalDivider()
                    }

                    item {
                        Text(
                            text = "MOVIES",
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    items(movies) { movie ->

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(movie.title)
                            Text(movie.genreIds.toString())
                        }
                    }
                }
            }
        }
    }
}