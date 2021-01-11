package com.arctouch.io.outdoorsychallenge.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.arctouch.io.outdoorsychallenge.extensions.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val repository: IVehicleRepository,
    dispatcherMap: DispatcherMap
) : ErrorHandlingViewModel(dispatcherMap) {

    private val _vehicles = MutableLiveData<List<Vehicle>>(null)
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    val emptyStateIsVisible: LiveData<Boolean> = _vehicles.map { it.isNullOrEmpty() }

    fun onTabSelected() = updateFavorites()

    fun onSwipeToRefresh() = updateFavorites()

    private fun updateFavorites() {
        viewModelScope.launch {
            val vehicles = withContext(Dispatchers.IO) {
                repository.getFavoriteVehicles()
            }
            _vehicles.value = vehicles
        }
    }
}
