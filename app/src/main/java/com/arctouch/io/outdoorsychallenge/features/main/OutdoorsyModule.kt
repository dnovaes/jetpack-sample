package com.arctouch.io.outdoorsychallenge.features.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val outdoorsyModule = module {
    viewModel { OutdoorsyViewModel(dispatcherMap = get(), getVehicleListByJson = get()) }
}
