package com.arctouch.io.outdoorsychallenge.features.outdoorsy

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val outdoorsyModule = module {
    viewModel { OutdoorsyViewModel() }
}
