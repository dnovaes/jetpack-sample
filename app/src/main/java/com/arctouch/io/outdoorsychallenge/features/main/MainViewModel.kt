package com.arctouch.io.outdoorsychallenge.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetFavoritesJsonValueUseCase
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListFromJson
import com.arctouch.io.outdoorsychallenge.domain.usecase.GetVehicleListFromJsonUseCase
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent

class MainViewModel(
    dispatcherMap: DispatcherMap,
    private val vehicleListFromJsonUseCase: GetVehicleListFromJsonUseCase,
    private val favoritesJsonValueUseCase: GetFavoritesJsonValueUseCase
) : ErrorHandlingViewModel(dispatcherMap) {

    val searchInput = MutableLiveData<String>()

    private val _searchButtonClickEvent = SingleLiveEvent<String?>()
    val searchButtonClickEvent: LiveData<String?> get() = _searchButtonClickEvent

    private val _qrCodeListReceivedEvent = SingleLiveEvent<List<Vehicle>>()
    val qrCodeListReceivedEvent: LiveData<List<Vehicle>> get() = _qrCodeListReceivedEvent

    private val _onFavoritesTabSelected = SingleLiveEvent<Any>()
    val onFavoritesTabSelected: LiveData<Any> get() = _onFavoritesTabSelected

    private val _qrCodeGeneratedEvent = SingleLiveEvent<String>()
    val qrCodeGeneratedEvent: LiveData<String?> get() = _qrCodeGeneratedEvent

    fun onSearchRvButtonClicked() = _searchButtonClickEvent.postValue(searchInput.value)

    fun onQrCodeListReceived(qrCodeJson: String) =
        _qrCodeListReceivedEvent.postValue(vehicleListFromJsonUseCase(qrCodeJson))

    fun onFavoritesTabSelected() = _onFavoritesTabSelected.postCall()

    fun onGenerateQrCodeButtonClicked() =
        performRequestSafely { _qrCodeGeneratedEvent.postValue(favoritesJsonValueUseCase()) }
}
