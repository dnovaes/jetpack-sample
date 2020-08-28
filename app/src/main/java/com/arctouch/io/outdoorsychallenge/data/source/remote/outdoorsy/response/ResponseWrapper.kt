package com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.response

data class ResponseWrapper<T, R>(val data: List<T>, val included: List<R>)

