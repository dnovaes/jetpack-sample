package com.arctouch.io.outdoorsychallenge.features.searchrv

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchRvModule = module {
    viewModel { SearchRvViewModel(repository = get(), dispatcherMap = get()) }
}
