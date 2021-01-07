package com.arctouch.io.outdoorsychallenge.domain

import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.MainDispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import com.arctouch.io.outdoorsychallenge.domain.model.mapper.VehicleMapper
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.arctouch.io.outdoorsychallenge.domain.repository.VehicleRepository
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListJsonValue
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListJsonValueUseCase
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListByJson
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListByJsonUseCase
import org.koin.dsl.module

val dispatchersModule = module {
    single<DispatcherMap> { MainDispatcherMap() }
}

val factoryModule = module {
    single { VehicleFactory() }
}

val mapperModule = module {
    single { VehicleMapper() }
}

val repositoryModule = module {
    single<IVehicleRepository> {
        VehicleRepository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

val useCaseModule = module {
    single<GetVehicleListJsonValueUseCase> { GetVehicleListJsonValue(vehicleCache = get()) }

    single<GetVehicleListByJsonUseCase> { GetVehicleListByJson(vehicleCache = get()) }
}

val domainModule = listOf(
    dispatchersModule,
    factoryModule,
    mapperModule,
    repositoryModule,
    useCaseModule
)
