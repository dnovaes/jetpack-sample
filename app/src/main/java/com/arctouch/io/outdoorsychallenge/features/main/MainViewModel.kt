package com.arctouch.io.outdoorsychallenge.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListJsonValueUseCase
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent

class MainViewModel(
    dispatcherMap: DispatcherMap,
    private val vehicleJsonValueUseCase: GetVehicleListJsonValueUseCase
) : ErrorHandlingViewModel(dispatcherMap) {

    val searchInput = MutableLiveData<String>()

    private val _searchButtonClickEvent = SingleLiveEvent<String?>()
    val searchButtonClickEvent: LiveData<String?> get() = _searchButtonClickEvent

    private val _qrCodeListReceivedEvent = SingleLiveEvent<Any>()
    val qrCodeListReceivedEvent: LiveData<Any> get() = _qrCodeListReceivedEvent

    private val _onFavoritesTabSelected = SingleLiveEvent<Any>()
    val onFavoritesTabSelected: LiveData<Any> get() = _onFavoritesTabSelected

    fun onSearchRvButtonClicked() = _searchButtonClickEvent.postValue(searchInput.value)

    fun onQrCodeListReceived() = _qrCodeListReceivedEvent.postCall()

    fun onFavoritesTabSelected() = _onFavoritesTabSelected.postCall()

    fun getResultJson(): String = vehicleJsonValueUseCase.invoke()
}
