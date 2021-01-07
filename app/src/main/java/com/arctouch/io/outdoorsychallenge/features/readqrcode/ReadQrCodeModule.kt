package com.arctouch.io.outdoorsychallenge.features.readqrcode

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val readQrCodeModule = module {
    viewModel { ReadQrCodeViewModel(dispatcherMap = get()) }
}
