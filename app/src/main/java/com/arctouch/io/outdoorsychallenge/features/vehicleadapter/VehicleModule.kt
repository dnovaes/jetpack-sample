package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import org.koin.dsl.module

val vehicleModule = module {
    factory { (vehicle: Vehicle) -> VehicleViewModel(vehicle = vehicle) }
}
