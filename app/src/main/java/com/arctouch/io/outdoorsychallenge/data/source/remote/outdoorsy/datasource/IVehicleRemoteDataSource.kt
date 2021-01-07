package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleRemoteDataSource {
    suspend fun fetchVehicles(query: String, pageLimit: Int, pageOffset: Int): List<Vehicle>?
}
