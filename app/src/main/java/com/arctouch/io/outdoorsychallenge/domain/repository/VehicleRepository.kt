package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.IVehicleRemoteDataSource

class VehicleRepository(private val dataSource: IVehicleRemoteDataSource) : IVehicleRepository {
    override suspend fun getVehiclesBy(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = dataSource.fetchVehicles(query, pageLimit, pageOffset).orEmpty()
}
