package com.arctouch.io.outdoorsychallenge.data.source.database

import com.arctouch.io.outdoorsychallenge.domain.dao.VehicleDao
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleDatabaseSource(
    private val vehicleDao: VehicleDao
) : IVehicleDatabaseSource{
    override suspend fun getAllFavorites(): List<Vehicle> = vehicleDao.getAllFavorites()
}
