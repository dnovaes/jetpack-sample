package com.arctouch.io.outdoorsychallenge.domain.usecase

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import com.arctouch.io.outdoorsychallenge.domain.repository.IVehicleRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

interface GetFavoritesJsonValueUseCase {

    suspend operator fun invoke(): String
}

class GetFavoritesJsonValue constructor(
    private val repository: IVehicleRepository,
    private val moshi: Moshi
) : GetFavoritesJsonValueUseCase {

    override suspend fun invoke(): String {
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            Vehicle::class.java
        )
        val adapter: JsonAdapter<List<*>> = moshi.adapter(type)

        return adapter.toJson(repository.getFavoriteVehicles())
    }
}
