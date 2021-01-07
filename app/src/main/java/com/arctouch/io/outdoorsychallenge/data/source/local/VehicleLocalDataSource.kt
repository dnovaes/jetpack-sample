package com.arctouch.io.outdoorsychallenge.data.source.local

import androidx.lifecycle.LiveData
import com.arctouch.io.outdoorsychallenge.data.source.local.database.dao.VehicleDao
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.model.mapper.VehicleMapperAlias
import com.arctouch.io.outdoorsychallenge.extensions.map

class VehicleLocalDataSource(
    private val vehicleDao: VehicleDao,
    private val mapper: VehicleMapperAlias
) : IVehicleLocalDataSource {
    override suspend fun add(vehicle: Vehicle) = vehicleDao.insert(mapper.fromDomain(vehicle))

    override suspend fun remove(vehicle: Vehicle) = vehicleDao.deleteBy(vehicle.id)

    override fun observeBy(id: String): LiveData<Vehicle> =
        vehicleDao.observeBy(id).map { mapper.toDomain(it) }

    override suspend fun getAll(): List<Vehicle> = vehicleDao.getAll().map { mapper.toDomain(it) }
}
