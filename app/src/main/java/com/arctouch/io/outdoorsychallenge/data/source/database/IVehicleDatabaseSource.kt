package com.arctouch.io.outdoorsychallenge.data.source.database

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleDatabaseSource {
    suspend fun getAllFavorites(): List<Vehicle>
}
