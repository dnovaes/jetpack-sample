package com.arctouch.io.outdoorsychallenge.unitmocks

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.ResponseWrapper
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse.Attributes
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse.Relationships
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse.Relationships.PrimaryImage
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse.Relationships.PrimaryImage.Data
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleIncludedResponse
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
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
        Attributes("title$id"),
        Relationships(PrimaryImage(Data("$id", "images")))
    )

private fun mockVehicleIncludedResponse(id: Int) =
    VehicleIncludedResponse("$id", VehicleIncludedResponse.Attributes("url$id"))

fun mockVehicles(size: Int): List<Vehicle> =
    mutableListOf<Vehicle>().apply { repeat(size) { this += mockVehicle(it) } }

private fun mockVehicle(id: Int) = Vehicle("$id", "title$id", "url$id")
