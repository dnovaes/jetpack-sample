package com.arctouch.io.outdoorsychallenge.features.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.arctouch.io.outdoorsychallenge.extensions.map
import com.arctouch.io.outdoorsychallenge.tools.ActivityAwareLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val repository: IVehicleRepository,
    dispatcherMap: DispatcherMap
) : ErrorHandlingViewModel(dispatcherMap) {

    private val _vehicles =  ActivityAwareLiveData<List<Vehicle>>(
        initialValue = null,
        onActiveListener = ::getFavoritedVehicles
    )
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    val emptyStateIsVisible: LiveData<Boolean> = _vehicles.map { it.isNullOrEmpty() }

    private fun getFavoritedVehicles() {
        viewModelScope.launch {
            val vehicles = withContext(Dispatchers.IO) {
                repository.getFavoriteVehicles()
            }
            _vehicles.value = vehicles
        }
    }
}
