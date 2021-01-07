package com.arctouch.io.outdoorsychallenge.features

import com.arctouch.io.outdoorsychallenge.features.main.outdoorsyModule
import com.arctouch.io.outdoorsychallenge.features.readqrcode.readQrCodeModule
import com.arctouch.io.outdoorsychallenge.features.searchrv.adapters.searchRvVehicleModule
import com.arctouch.io.outdoorsychallenge.features.searchrv.searchRvModule

val featuresModule = outdoorsyModule + searchRvModule + readQrCodeModule + searchRvVehicleModule
