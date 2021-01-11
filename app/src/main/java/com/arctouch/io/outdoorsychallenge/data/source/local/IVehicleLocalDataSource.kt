package com.arctouch.io.outdoorsychallenge.data.source.local

import androidx.lifecycle.LiveData
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleLocalDataSource {
    suspend fun add(vehicle: Vehicle)

    suspend fun remove(vehicle: Vehicle)

    suspend fun isFavorite(id: String): Boolean

    fun observeFavoriteStatusBy(id: String): LiveData<Boolean>

    suspend fun getAll(): List<Vehicle>
}
