package com.arctouch.io.outdoorsychallenge.domain.usecase

import com.arctouch.io.outdoorsychallenge.data.source.local.cache.VehicleCache
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


interface GetVehicleListJsonValueUseCase {

    operator fun invoke(): String
}

class GetVehicleListJsonValue constructor(
    private val vehicleCache: VehicleCache
) : GetVehicleListJsonValueUseCase {

    override fun invoke(): String {
        val moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            Vehicle::class.java
        )
        val adapter: JsonAdapter<List<*>> = moshi.adapter(type)

        return adapter.toJson(vehicleCache.items)
    }
}
