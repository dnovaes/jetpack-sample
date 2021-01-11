package com.arctouch.io.outdoorsychallenge.features

import com.arctouch.io.outdoorsychallenge.features.favorites.favoritesModule
import com.arctouch.io.outdoorsychallenge.features.qrcoderesult.qrCodeResultModule
import com.arctouch.io.outdoorsychallenge.features.main.mainModule
import com.arctouch.io.outdoorsychallenge.features.outdoorsy.outdoorsyModule
import com.arctouch.io.outdoorsychallenge.features.readqrcode.readQrCodeModule
import com.arctouch.io.outdoorsychallenge.features.searchrv.searchRvModule
import com.arctouch.io.outdoorsychallenge.features.vehicleadapter.vehicleModule
import com.arctouch.io.outdoorsychallenge.features.showqrcode.showQrCodeModule

val featuresModule = listOf(
    outdoorsyModule,
    mainModule,
    searchRvModule,
    favoritesModule,
    qrCodeResultModule,
    readQrCodeModule,
    vehicleModule,
    showQrCodeModule
)
