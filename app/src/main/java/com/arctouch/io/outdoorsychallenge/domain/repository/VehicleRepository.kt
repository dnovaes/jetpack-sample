package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.data.source.local.IVehicleLocalDataSource
import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache.Origin.QRCODE
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleRepository(
    private val remoteDataSource: IVehicleRemoteDataSource,
    private val localDataSource: IVehicleLocalDataSource,
    private val cache: VehicleCache
) : IVehicleRepository {

    override suspend fun getVehiclesBy(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = remoteDataSource.fetchVehicles(query, pageLimit, pageOffset).orEmpty()

    override suspend fun getFavoriteVehicles(): List<Vehicle> = localDataSource.getAll()

    override fun getQrCodeResultVehicles(): List<Vehicle> =
        if (cache.origin == QRCODE) cache.items else mutableListOf()
}
