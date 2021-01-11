package com.arctouch.io.outdoorsychallenge.features.qrcoderesult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QrCodeResultViewModel(
    private val repository: IVehicleRepository,
    dispatcherMap: DispatcherMap
) : ErrorHandlingViewModel(dispatcherMap) {

    private val _vehicles =  MutableLiveData<List<Vehicle>>()
    val vehicles: LiveData<List<Vehicle>> = _vehicles

    private val _emptyStateIsVisible = MutableLiveData<Boolean>(true)
    val emptyStateIsVisible: LiveData<Boolean> get() = _emptyStateIsVisible
}
