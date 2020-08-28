package com.arctouch.io.outdoorsychallenge.domain.model.factory

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleDataResponse
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response.VehicleIncludedResponse
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

typealias VehicleFactoryAlias =
    ModelFactory<Pair<VehicleDataResponse, List<VehicleIncludedResponse>>, Vehicle?>

class VehicleFactory : VehicleFactoryAlias {
    override fun make(input: Pair<VehicleDataResponse, List<VehicleIncludedResponse>>) =
        with(input) {
            Vehicle(
                id = first.id,
                name = first.attributes?.name?.ifBlank { null }?.trim(),
                imageUrl = getImageUrlBy(first.relationships?.primary_image?.data?.id, second)
            )
        }

    private fun getImageUrlBy(id: String?, includedInfo: List<VehicleIncludedResponse>) =
        id?.let {
            includedInfo
                .firstOrNull { it.id == id }
                ?.attributes
                ?.url
                ?.ifBlank { null }
                ?.trim()
        }
}
