package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.data.source.local.IVehicleLocalDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleRepository(
    private val remoteDataSource: IVehicleRemoteDataSource,
    private val localDataSource: IVehicleLocalDataSource
) : IVehicleRepository {

    override suspend fun getFavoriteVehicles(): List<Vehicle> = localDataSource.getAll()

    override suspend fun getVehiclesBy(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = remoteDataSource.fetchVehicles(query, pageLimit, pageOffset).orEmpty()
}
