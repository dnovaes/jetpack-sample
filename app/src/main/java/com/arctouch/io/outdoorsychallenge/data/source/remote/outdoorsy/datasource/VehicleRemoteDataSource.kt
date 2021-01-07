package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource

import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache.Origin.QRCODE
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyApi
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactoryAlias
import com.arctouch.io.outdoorsychallenge.features.searchrv.SearchRvFragment.Companion.QR_CODE_RESULT

class VehicleRemoteDataSource(
    private val api: OutdoorsyApi,
    private val factory: VehicleFactoryAlias,
    private val cache: VehicleCache
) : IVehicleRemoteDataSource {
    override suspend fun fetchVehicles(
        query: String,
        pageLimit: Int,
        pageOffset: Int
    ): List<Vehicle>? {
        val isFirstPage = pageLimit >= pageOffset

        return if (cache.origin == QRCODE && cache.items.isNotEmpty() && isFirstPage) cache.items
        else if (query != QR_CODE_RESULT)
            api.fetchVehiclesAsync(query, pageLimit, pageOffset)
                .await()
                .let {
                    it?.data?.mapNotNull { vehicleResponse ->
                        factory.make(Pair(vehicleResponse, it.included))
                    }?.also { vehicles ->
                        if (isFirstPage) {
                            cache.origin = VehicleCache.Origin.API
                            cache.items = vehicles
                        }
                    }
                }
        else mutableListOf()
    }
}
