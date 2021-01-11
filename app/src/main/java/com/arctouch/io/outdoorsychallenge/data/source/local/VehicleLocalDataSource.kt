package com.arctouch.io.outdoorsychallenge.data.source.local

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

    override suspend fun isFavorite(id: String) = vehicleDao.getBy(id) != null

    override fun observeFavoriteStatusBy(id: String) = vehicleDao.observeBy(id).map { it != null }

    override suspend fun getAll(): List<Vehicle> = vehicleDao.getAll().map { mapper.toDomain(it) }
}
