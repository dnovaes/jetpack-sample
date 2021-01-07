package com.arctouch.io.outdoorsychallenge.data.source.local.cache

interface ListCache<T> {

    var items: List<T>

    operator fun plusAssign(value: List<T>)

    fun clear()
}
