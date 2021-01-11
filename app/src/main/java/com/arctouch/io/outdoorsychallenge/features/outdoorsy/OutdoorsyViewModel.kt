package com.arctouch.io.outdoorsychallenge.features.outdoorsy

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListByJsonUseCase
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent
import kotlinx.coroutines.launch

class OutdoorsyViewModel(
    dispatcherMap: DispatcherMap,
    private val getVehicleListByJson: GetVehicleListByJsonUseCase
) : ErrorHandlingViewModel(dispatcherMap) {

    private val _qrCodeEvent = SingleLiveEvent<Any>()
    val qrCodeEvent: LiveData<Any> get() = _qrCodeEvent

    fun onQrCodeRead(qrCodeValue: String) {
        viewModelScope.launch {
            getVehicleListByJson(qrCodeValue)
            _qrCodeEvent.postCall()
        }
    }
}
