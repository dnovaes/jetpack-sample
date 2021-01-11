package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyApi
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactoryAlias

class VehicleRemoteDataSource(
    private val api: OutdoorsyApi,
    private val factory: VehicleFactoryAlias
) : IVehicleRemoteDataSource {
    override suspend fun fetchVehicles(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = api.fetchVehiclesAsync(query, pageLimit, pageOffset)
        .await()
        .let { it?.data?.mapNotNull { response -> factory.make(Pair(response, it.included)) } }
}
