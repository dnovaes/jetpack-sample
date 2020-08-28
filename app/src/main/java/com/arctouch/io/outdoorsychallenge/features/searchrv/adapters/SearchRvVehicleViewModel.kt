package com.arctouch.io.outdoorsychallenge.features.searchrv.adapters

import androidx.lifecycle.ViewModel
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class SearchRvVehicleViewModel(vehicle: Vehicle) : ViewModel() {

    val name = vehicle.name
    val imageUrl = vehicle.imageUrl
}
