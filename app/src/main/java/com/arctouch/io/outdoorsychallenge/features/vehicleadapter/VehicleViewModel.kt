package com.arctouch.io.outdoorsychallenge.features.vehicleadapter

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleViewModel(
    private val dispatcherMap: DispatcherMap,
    private val vehicle: Vehicle,
    private val repository: IVehicleRepository
) : ViewModel() {

    val id = vehicle.id
    val name = vehicle.name
    val imageUrl = vehicle.imageUrl

    var isFavorite = MediatorLiveData<Boolean>().apply { value = false }

    init {
        viewModelScope.launch(dispatcherMap.ui) { updateFavoriteStatus() }
    }

    private suspend fun updateFavoriteStatus() {
        val status = withContext(dispatcherMap.io) { repository.isFavorite(vehicle) }
        if (status) {
            val favoriteStatusEvent = repository.observeFavoriteStatusBy(vehicle.id)
            isFavorite.addSource(favoriteStatusEvent) {
                isFavorite.postValue(it)
                if (!it) isFavorite.removeSource(favoriteStatusEvent)
            }
        }
    }

    fun toggleFavoriteStatus() {
        viewModelScope.launch(dispatcherMap.ui) {
            withContext(dispatcherMap.io) {
                if (isFavorite.value == true) repository.removeFavorite(vehicle)
                else repository.addFavorite(vehicle)
            }

            updateFavoriteStatus()
        }
    }
}
