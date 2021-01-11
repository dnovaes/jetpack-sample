package com.arctouch.io.outdoorsychallenge.features.outdoorsy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent

class OutdoorsyViewModel : ViewModel() {

    private val _qrCodeEvent = SingleLiveEvent<String>()
    val qrCodeEvent: LiveData<String> get() = _qrCodeEvent

    fun onQrCodeRead(qrCodeValue: String) = _qrCodeEvent.postValue(qrCodeValue)
}
