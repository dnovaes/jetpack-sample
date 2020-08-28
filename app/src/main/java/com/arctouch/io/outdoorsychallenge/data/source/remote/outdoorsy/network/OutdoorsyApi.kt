package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.ResponseWrapper
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleIncludedResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface OutdoorsyApi {
    @GET("/rentals")
    fun fetchVehiclesAsync(
        @Query("filter[keywords]") query: String,
        @Query("page[limit]") pageLimit: Int,
        @Query("page[offset]") pageOffset: Int,
        @Query("address") address: String = "San Francisco, CA, USA"
    ): Deferred<ResponseWrapper<VehicleDataResponse, VehicleIncludedResponse>?>
}
