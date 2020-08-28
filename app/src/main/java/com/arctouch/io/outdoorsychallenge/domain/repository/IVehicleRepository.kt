package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleRepository {
    suspend fun getVehiclesBy(query: String, pageLimit: Int, pageOffset: Int): List<Vehicle>
}
