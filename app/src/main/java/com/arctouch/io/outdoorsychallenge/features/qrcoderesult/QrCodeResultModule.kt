package com.arctouch.io.outdoorsychallenge.features.qrcoderesult

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val qrCodeResultModule = module {
    viewModel {
        QrCodeResultViewModel(dispatcherMap = get())
    }
}
