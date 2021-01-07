package com.arctouch.io.outdoorsychallenge.domain.model.mapper

import com.arctouch.io.outdoorsychallenge.data.source.local.database.entity.VehicleEntity
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleIncludedResponse
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

typealias VehicleMapperAlias = Mapper<VehicleEntity, Vehicle>

class VehicleMapper : VehicleMapperAlias {
    override fun toDomain(entity: VehicleEntity) = Vehicle(
        id = entity.id,
        name = entity.name,
        imageUrl = entity.imageUrl,
        isFavorite = true
    )

    override fun fromDomain(domain: Vehicle) = VehicleEntity(
        id = domain.id,
        name = domain.name,
        imageUrl = domain.imageUrl
    )
}
