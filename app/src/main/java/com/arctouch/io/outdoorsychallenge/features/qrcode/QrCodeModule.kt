package com.arctouch.io.outdoorsychallenge.features.qrcode

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val qrCodeModule = module {
    viewModel { QrCodeViewModel(dispatcherMap = get()) }
}
