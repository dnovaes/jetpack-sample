package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource

import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyApi
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactoryAlias

class VehicleRemoteDataSource(
    private val api: OutdoorsyApi,
    private val factory: VehicleFactoryAlias,
    private val cache: VehicleCache
) : IVehicleRemoteDataSource {
    override suspend fun fetchVehicles(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ) = api.fetchVehiclesAsync(query, pageLimit, pageOffset)
        .await()
        .let {
            it?.data?.mapNotNull { vehicleResponse ->
                factory.make(Pair(vehicleResponse, it.included))
            }?.also { vehicles ->
                val isFirstPage = pageLimit > pageOffset

                if (isFirstPage) {
                    cache.origin = VehicleCache.Origin.API
                    cache.items = vehicles
                }
            }
        }
}
