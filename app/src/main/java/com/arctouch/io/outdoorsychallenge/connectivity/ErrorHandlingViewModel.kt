package com.arctouch.io.outdoorsychallenge.connectivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.io.outdoorsychallenge.connectivity.Cause.ERROR
import com.arctouch.io.outdoorsychallenge.connectivity.Cause.NO_INTERNET
import com.arctouch.io.outdoorsychallenge.domain.dispatchers.DispatcherMap
import com.arctouch.io.outdoorsychallenge.tools.SingleLiveEvent
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

abstract class ErrorHandlingViewModel(private val dispatcherMap: DispatcherMap) : ViewModel() {

    private val _networkErrorEvent = SingleLiveEvent<Any>()
    val networkErrorEvent: LiveData<Any> get() = _networkErrorEvent

    private val _genericErrorEvent = SingleLiveEvent<Any>()
    val genericErrorEvent: LiveData<Any> get() = _genericErrorEvent

    protected fun performRequestSafely(
        onError: (Cause) -> Unit = {},
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcherMap.ui) {
            try {
                withContext(dispatcherMap.io) { block.invoke() }
            } catch (exception: ConnectException) {
                onLoadException(NO_INTERNET, onError)
            } catch (exception: UnknownHostException) {
                onLoadException(NO_INTERNET, onError)
            } catch (exception: HttpException) {
                onLoadException(ERROR, onError)
            } catch (exception: IOException) {
                onLoadException(ERROR, onError)
            } catch (exception: JsonDataException) {
                onLoadException(ERROR, onError)
            } catch (exception: NoSuchElementException) {
                onLoadException(ERROR, onError)
            }
        }
    }

    private fun onLoadException(cause: Cause, onError: (Cause) -> Unit = {}) {
        when (cause) {
            NO_INTERNET -> _networkErrorEvent.call()
            ERROR -> _genericErrorEvent.call()
        }
        onError(cause)
    }
}

enum class Cause {
    NO_INTERNET,
    ERROR
}
