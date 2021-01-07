package com.arctouch.io.outdoorsychallenge.data.source.local

import androidx.lifecycle.LiveData
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleLocalDataSource {
    suspend fun add(vehicle: Vehicle)

    suspend fun remove(vehicle: Vehicle)

    fun observeBy(id: String): LiveData<Vehicle>

    suspend fun getAll(): List<Vehicle>
}
