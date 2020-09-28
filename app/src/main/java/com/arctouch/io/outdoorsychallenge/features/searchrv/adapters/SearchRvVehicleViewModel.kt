package com.arctouch.io.outdoorsychallenge.features.searchrv.adapters

import androidx.lifecycle.ViewModel
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import timber.log.Timber

class SearchRvVehicleViewModel(vehicle: Vehicle) : ViewModel() {

    val id = vehicle.id
    val name = vehicle.name
    val imageUrl = vehicle.imageUrl
    val isFavorite = vehicle.isFavorite

    fun onSetFavorite(id: String?) {
        Timber.v("logd insert vehicle in roomDatabase with same id $id")
    }
}
