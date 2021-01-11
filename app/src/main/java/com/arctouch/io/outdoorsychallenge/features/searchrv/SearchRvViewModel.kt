package com.arctouch.io.outdoorsychallenge.features.searchrv

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

class SearchRvViewModel(
    private val repository: IVehicleRepository,
    dispatcherMap: DispatcherMap,
    private val vehicleJsonValueUseCase: GetVehicleListJsonValueUseCase
) : ErrorHandlingViewModel(dispatcherMap) {

    private var searchInput: String? = null

    val vehicles: LiveData<PagedList<Vehicle>> = buildPagedListLiveData()

    private val _emptyStateIsVisible = MutableLiveData<Boolean>(true)
    val emptyStateIsVisible: LiveData<Boolean> get() = _emptyStateIsVisible

    private val _progressIsVisible = MutableLiveData(false)
    val progressIsVisible: LiveData<Boolean> get() = _progressIsVisible

    private val _paginationProgressIsVisible = MutableLiveData(false)
    val paginationProgressIsVisible: LiveData<Boolean> get() = _paginationProgressIsVisible

    private lateinit var pagingDataSource: VehiclePagingDataSource

    private fun buildPagedListLiveData() =
        LivePagedListBuilder(
            object : DataSource.Factory<Int, Vehicle>() {
                override fun create() = VehiclePagingDataSource().also { pagingDataSource = it }
            },
            Config(
                RV_LIST_PAGE_LIMIT,
                enablePlaceholders = false,
                initialLoadSizeHint = RV_LIST_PAGE_LIMIT
            )
        ).build()

    fun onSearchRvButtonClicked(searchInput: String?) {
        this.searchInput = searchInput
        pagingDataSource.invalidate()
    }

    fun onSwipeToRefresh() = pagingDataSource.invalidate()
  
    fun onQrCodeListReceived() = pagingDataSource.invalidate()

    fun getResultJson(): String = vehicleJsonValueUseCase.invoke()

    private inner class VehiclePagingDataSource : PageKeyedDataSource<Int, Vehicle>() {
        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Vehicle>) =
            Unit

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Vehicle>
        ) {
            val query = searchInput ?: run {
                _progressIsVisible.postValue(false)
                return
            }

            performRequestSafely(onError = { onPageError() }) {
                _progressIsVisible.postValue(true)

                load(query, 0).let {
                    _emptyStateIsVisible.postValue(it.isEmpty())
                    callback.onResult(it, null, 1)
                }

                _progressIsVisible.postValue(false)
            }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Vehicle>) {
            val query = searchInput ?: run {
                _progressIsVisible.postValue(false)
                return
            }

            performRequestSafely(onError = { onPageError() }) {
                _paginationProgressIsVisible.postValue(true)

                callback.onResult(load(query, RV_LIST_PAGE_LIMIT * params.key), params.key + 1)

                _paginationProgressIsVisible.postValue(false)
            }
        }

        private suspend fun load(query: String, pageOffset: Int): List<Vehicle> =
            repository.getVehiclesBy(query, RV_LIST_PAGE_LIMIT, pageOffset).toMutableList()

        private fun onPageError() {
            _progressIsVisible.postValue(false)
            _paginationProgressIsVisible.postValue(false)
        }
    }

    companion object {

        const val RV_LIST_PAGE_LIMIT = 8
    }
}
