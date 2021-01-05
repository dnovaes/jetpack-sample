package com.arctouch.io.outdoorsychallenge.features.main

import androidx.lifecycle.LiveData
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent

class OutdoorsyViewModel(dispatcherMap: DispatcherMap) : ErrorHandlingViewModel(dispatcherMap){

    private val _qrCodeEvent = SingleLiveEvent<String>()
    val qrCodeEvent: LiveData<String> get() = _qrCodeEvent

    fun onQrCodeRead(qrCodeValue: String) = _qrCodeEvent.postValue(qrCodeValue)
}
