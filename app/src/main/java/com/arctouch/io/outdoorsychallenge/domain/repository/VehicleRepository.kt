package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.data.source.database.IVehicleDatabaseSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleRepository(
    private val dataSource: IVehicleRemoteDataSource,
    private val databaseSource: IVehicleDatabaseSource
) : IVehicleRepository {

    override suspend fun getFavoriteVehicles(): List<Vehicle> = databaseSource.getAllFavorites()

    override suspend fun getVehiclesBy(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = dataSource.fetchVehicles(query, pageLimit, pageOffset).orEmpty()

}
