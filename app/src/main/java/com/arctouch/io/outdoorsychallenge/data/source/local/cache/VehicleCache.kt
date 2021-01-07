package com.arctouch.io.outdoorsychallenge.data.source.local.cache

import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

class VehicleCache : ListCache<Vehicle> {

    var origin: Origin = Origin.NONE
    var searchText: String? = null

    private val _items = mutableListOf<Vehicle>()
    override var items: List<Vehicle>
        get() = _items
        set(value) {
            clear()
            _items.addAll(value)
        }

    override fun plusAssign(value: List<Vehicle>) {
        items = items.plus(value)
    }

    override fun clear() = _items.clear()

    enum class Origin {
        API, QRCODE, NONE
    }
}