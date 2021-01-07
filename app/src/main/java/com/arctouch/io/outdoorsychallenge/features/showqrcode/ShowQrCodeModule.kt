package com.arctouch.io.outdoorsychallenge.features.showqrcode

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val showQrCodeModule = module {
    viewModel { ShowQrCodeViewModel(dispatcherMap = get()) }
}