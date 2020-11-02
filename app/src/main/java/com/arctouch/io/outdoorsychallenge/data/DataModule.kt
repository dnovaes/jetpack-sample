package com.arctouch.io.outdoorsychallenge.data

import androidx.room.Room
import com.arctouch.io.outdoorsychallenge.data.source.database.IVehicleDatabaseSource
import com.arctouch.io.outdoorsychallenge.data.source.database.VehicleDatabaseSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.VehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyClientBuilder
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import org.koin.android.ext.koin.androidContext
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

    single<IVehicleDatabaseSource> {
        VehicleDatabaseSource(
            vehicleDao = get<VehicleDatabase>().vehicleDao()
        )
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            VehicleDatabase::class.java,
            VehicleDatabase.TABLE_NAME
        ).build()
    }
}

val dataModule = networkModule + dataSourceModule
