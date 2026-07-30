package com.impl.di

import com.impl.screen.details.mapper.MovieDetailsUiMapper
import com.impl.screen.details.vm.DetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    single { MovieDetailsUiMapper() }
    viewModel { (movieId: Int) ->
        DetailsViewModel(
            movieId = movieId,
            getMovieByIdUseCase = get(),
            addFavoriteUseCase = get(),
            removeFavoriteUseCase = get(),
            isFavoriteUseCase = get(),
            getGenresUseCase = get(),
            movieDetailsUiMapper = get()
        )
    }
}