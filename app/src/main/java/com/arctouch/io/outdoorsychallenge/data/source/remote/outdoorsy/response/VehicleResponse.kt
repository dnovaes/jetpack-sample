package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response

data class VehicleDataResponse(
    val id: String,
    val attributes: Attributes?,
    val relationships: Relationships?
) {

    data class Attributes(val name: String?)
    data class Relationships(val primary_image: PrimaryImage?) {

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

