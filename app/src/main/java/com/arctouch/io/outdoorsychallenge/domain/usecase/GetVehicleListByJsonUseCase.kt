package com.arctouch.io.outdoorsychallenge.domain.usecase

import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type


interface GetVehicleListByJsonUseCase {

    operator fun invoke(vehicleJson: String)
}

class GetVehicleListByJson constructor(
    private val vehicleCache: VehicleCache,
    private val moshi: Moshi
) : GetVehicleListByJsonUseCase {

    override fun invoke(vehicleJson: String) {
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            Vehicle::class.java
        )
        val adapter: JsonAdapter<List<*>> = moshi.adapter(type)

        try {
            val vehicleList =
                adapter.fromJson(vehicleJson)?.map { it as Vehicle } ?: mutableListOf()

            vehicleCache.origin = VehicleCache.Origin.QRCODE
            vehicleCache.items = vehicleList
        } catch (e: IOException) {
            Timber.tag("QrCodeUtils").d( "QRCode parse error: ${e.message}")
        } catch (e: JsonDataException) {
            Timber.tag("QrCodeUtils").d( "QRCode parse error: ${e.message}")
        }
    }
}
