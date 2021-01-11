package com.arctouch.io.outdoorsychallenge.features.qrcoderesult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.extensions.map

class QrCodeResultViewModel(dispatcherMap: DispatcherMap) : ErrorHandlingViewModel(dispatcherMap) {

    private val _vehicles = MutableLiveData<List<Vehicle>>(null)
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    val emptyStateIsVisible: LiveData<Boolean> = _vehicles.map { it.isNullOrEmpty() }

    fun onQrCodeListReceived(vehicles: List<Vehicle>) = _vehicles.postValue(vehicles)
}
