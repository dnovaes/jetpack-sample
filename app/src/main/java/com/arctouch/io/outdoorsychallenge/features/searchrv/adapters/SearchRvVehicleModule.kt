package com.arctouch.io.outdoorsychallenge.features.searchrv.adapters

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import org.koin.dsl.module

val searchRvVehicleModule = module {
    factory { (vehicle: Vehicle) -> SearchRvVehicleViewModel(vehicle = vehicle) }
}
