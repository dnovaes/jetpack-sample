package com.arctouch.io.outdoorsychallenge.features.favorites

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            repository = get(),
            dispatcherMap = get()
        )
    }
}
