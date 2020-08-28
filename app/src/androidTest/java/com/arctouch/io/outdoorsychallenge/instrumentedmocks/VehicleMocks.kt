package com.arctouch.io.outdoorsychallenge.instrumentedmocks

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.ResponseWrapper
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleIncludedResponse
import kotlinx.coroutines.CompletableDeferred

fun mockVehiclesResponse(size: Int) =
    CompletableDeferred(
        ResponseWrapper(
            data = mutableListOf<VehicleDataResponse>()
                .apply { repeat(size) { this += mockVehicleDataResponse(it) } },
            included = mutableListOf<VehicleIncludedResponse>()
                .apply { repeat(size) { this += mockVehicleIncludedResponse(it) } }
        )
    )

private fun mockVehicleDataResponse(id: Int) =
    VehicleDataResponse(
        "$id",
        VehicleDataResponse.Attributes("title$id"),
        VehicleDataResponse.Relationships(
            VehicleDataResponse.Relationships.PrimaryImage(
                VehicleDataResponse.Relationships.PrimaryImage.Data("$id", "images")
            )
        )
    )

private fun mockVehicleIncludedResponse(id: Int) =
    VehicleIncludedResponse("$id", VehicleIncludedResponse.Attributes("url$id"))
