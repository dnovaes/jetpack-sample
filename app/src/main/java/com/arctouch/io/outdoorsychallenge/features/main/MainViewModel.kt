package com.arctouch.io.outdoorsychallenge.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.arctouch.io.outdoorsychallenge.connectivity.ErrorHandlingViewModel
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun onSearchRvButtonClicked() = _searchButtonClickEvent.postValue(searchInput.value)
  
    fun onQrCodeListReceived() = _qrCodeListReceivedEvent.postCall()

    fun getResultJson(): String = vehicleJsonValueUseCase.invoke()
}
