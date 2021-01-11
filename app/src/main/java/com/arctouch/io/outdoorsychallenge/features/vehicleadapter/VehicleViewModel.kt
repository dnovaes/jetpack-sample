package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import androidx.lifecycle.ViewModel
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import timber.log.Timber

class VehicleViewModel(vehicle: Vehicle) : ViewModel() {

    val id = vehicle.id
    val name = vehicle.name
    val imageUrl = vehicle.imageUrl
    val isFavorite = vehicle.isFavorite

    fun onSetFavorite(id: String?) {
        Timber.v("log insert vehicle in roomDatabase with same id $id")
    }
}
