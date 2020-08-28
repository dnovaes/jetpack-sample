package com.arctouch.io.outdoorsychallenge.domain

import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.MainDispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.arctouch.io.outdoorsychallenge.domain.repository.VehicleRepository
import org.koin.dsl.module

val dispatchersModule = module {
    single<DispatcherMap> { MainDispatcherMap() }
}

val factoryModule = module {
    single { VehicleFactory() }
}

val repositoryModule = module {
    single<IVehicleRepository> { VehicleRepository(dataSource = get()) }
}

val domainModule = dispatchersModule + factoryModule + repositoryModule
