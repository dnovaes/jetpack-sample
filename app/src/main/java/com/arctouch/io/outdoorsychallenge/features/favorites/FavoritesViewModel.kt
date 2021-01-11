package com.arctouch.io.outdoorsychallenge.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.arctouch.io.outdoorsychallenge.extensions.map

class FavoritesViewModel(
    private val repository: IVehicleRepository,
    dispatcherMap: DispatcherMap
) : ErrorHandlingViewModel(dispatcherMap) {

    private val _vehicles = MutableLiveData<List<Vehicle>>(null)
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    val emptyStateIsVisible: LiveData<Boolean> = _vehicles.map { it.isNullOrEmpty() }

    fun onTabSelected() = updateFavorites()

    fun onSwipeToRefresh() = updateFavorites()

    private fun updateFavorites() =
        performRequestSafely { _vehicles.postValue(repository.getFavoriteVehicles()) }
}
