package com.arctouch.io.outdoorsychallenge.data

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.VehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyClientBuilder
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import org.koin.dsl.module

val networkModule = module {
    single { OutdoorsyClientBuilder().buildApiClient() }
}

val dataSourceModule = module {
    single<IVehicleRemoteDataSource> {
        VehicleRemoteDataSource(
            api = get(),
            factory = get<VehicleFactory>()
        )
    }
}

val dataModule = networkModule + dataSourceModule
