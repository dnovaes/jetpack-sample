package com.arctouch.io.outdoorsychallenge.domain.usecase

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type

interface GetVehicleListFromJsonUseCase {
    operator fun invoke(json: String): List<Vehicle>
}

class GetVehicleListFromJson constructor(private val moshi: Moshi) : GetVehicleListFromJsonUseCase {
    override fun invoke(json: String): List<Vehicle> {
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            Vehicle::class.java
        )
        val adapter = moshi.adapter<List<Vehicle>>(type)

        try {
            return adapter.fromJson(json) ?: emptyList()
        } catch (e: IOException) {
            Timber.tag("QrCodeUtils").d("QRCode parse error: ${e.message}")
        } catch (e: JsonDataException) {
            Timber.tag("QrCodeUtils").d("QRCode parse error: ${e.message}")
        }
        return emptyList()
    }
}
