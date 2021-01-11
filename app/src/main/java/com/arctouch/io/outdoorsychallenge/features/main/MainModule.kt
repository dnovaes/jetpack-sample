package com.arctouch.io.outdoorsychallenge.features.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(
            dispatcherMap = get(),
            vehicleJsonValueUseCase = get()
        )
    }
}
