package com.arctouch.io.outdoorsychallenge.data

import com.arctouch.io.outdoorsychallenge.data.source.local.IVehicleLocalDataSource
import com.arctouch.io.outdoorsychallenge.data.source.local.VehicleLocalDataSource
import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.data.source.local.database.VehicleDatabaseClient
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.VehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyClientBuilder
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import com.arctouch.io.outdoorsychallenge.domain.model.mapper.VehicleMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single { OutdoorsyClientBuilder.buildApiClient() }

    factory { OutdoorsyClientBuilder.buildMoshiAdapter() }
}

val databaseModule = module {
    single { VehicleDatabaseClient.setupDatabase(context = androidContext()) }

    single { get<VehicleDatabaseClient>().vehicleDao() }
}

val dataSourceModule = module {

    single<IVehicleRemoteDataSource> {
        VehicleRemoteDataSource(
            api = get(),
            factory = get<VehicleFactory>(),
            cache = get()
        )
    }

    single<IVehicleLocalDataSource> {
        VehicleLocalDataSource(
            vehicleDao = get(),
            mapper = get<VehicleMapper>()
        )
    }
}

val cacheModule = module {
    single { VehicleCache() }
}

val dataModule = networkModule + dataSourceModule + databaseModule + cacheModule