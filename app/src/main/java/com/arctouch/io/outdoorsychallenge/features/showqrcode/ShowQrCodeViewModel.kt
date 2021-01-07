package com.arctouch.io.outdoorsychallenge.features.showqrcode

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent
import kotlinx.coroutines.launch

class ShowQrCodeViewModel(
    private val dispatcherMap: DispatcherMap
) : ViewModel() {

    private val _shareEvent = SingleLiveEvent<Any>()
    val shareEvent: LiveData<Any> get() = _shareEvent

    private val _imageSavedEvent = SingleLiveEvent<Uri>()
    val imageSavedEvent: LiveData<Uri> get() = _imageSavedEvent

    fun shareButtonClicked() = _shareEvent.postCall()

    fun executeSaveImage(saveImage: (Bitmap) -> Uri?, image: Bitmap) {
        viewModelScope.launch(dispatcherMap.computation) {
            _imageSavedEvent.postValue(saveImage(image))
        }
    }
}
