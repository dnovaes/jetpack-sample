package com.arctouch.io.outdoorsychallenge.features.searchrv.adapters

import androidx.lifecycle.ViewModel
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class SearchRvVehicleViewModel(vehicle: Vehicle) : ViewModel() {

    val id = vehicle.id
    val name = vehicle.name
    val imageUrl = vehicle.imageUrl
    val favoriteResource = if (vehicle.isFavorite)
        R.drawable.icon_star_selected
    else
        R.drawable.icon_star_unselected

    fun onSetFavorite(id: String?) {
        println("logd insert vehicle in roomDatabase with same id $id")
    }
}
