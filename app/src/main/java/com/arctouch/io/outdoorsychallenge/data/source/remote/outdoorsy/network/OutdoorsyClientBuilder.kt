package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network

import com.arctouch.io.outdoorsychallenge.BuildConfig
import com.arctouch.io.outdoorsychallenge.data.source.remote.BaseClientBuilder
import com.squareup.moshi.Moshi

class OutdoorsyClientBuilder : BaseClientBuilder<OutdoorsyApi>() {

    override val baseUrl: String get() = BuildConfig.OUTDOORSY_URL

    override fun buildApiClient(): OutdoorsyApi = buildRetrofit().create(OutdoorsyApi::class.java)

    override fun buildMoshiAdapter(): Moshi = Moshi.Builder().build()
}
