package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehicleDataResponse(
    val id: String,
    val attributes: Attributes?,
    val relationships: Relationships?
) {

    data class Attributes(val name: String?)
    data class Relationships(@Json(name = "primary_image") val primaryImage: PrimaryImage?) {

        data class PrimaryImage(val data: Data?) {

            data class Data(val id: String?, val type: String?)
        }
    }
}

data class VehicleIncludedResponse(
    val id: String,
    val attributes: Attributes?
) {

    data class Attributes(val url: String?)
}

