package com.impl.screen.splash.module

import com.impl.screen.splash.vm.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(getPopularMovies = get()) }
}