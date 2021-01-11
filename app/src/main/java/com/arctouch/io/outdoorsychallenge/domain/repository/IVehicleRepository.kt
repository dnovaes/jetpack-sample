package com.arctouch.io.outdoorsychallenge.domain.repository

import androidx.lifecycle.LiveData
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

interface IVehicleRepository {

    suspend fun getVehiclesBy(query: String, pageLimit: Int, pageOffset: Int): List<Vehicle>

    suspend fun getFavoriteVehicles(): List<Vehicle>

    suspend fun addFavorite(vehicle: Vehicle)

    suspend fun removeFavorite(vehicle: Vehicle)

    suspend fun isFavorite(vehicle: Vehicle): Boolean

    fun observeFavoriteStatusBy(id: String): LiveData<Boolean>

    fun getQrCodeResultVehicles(): List<Vehicle>
}
