package com.arctouch.io.outdoorsychallenge.domain.model.factory

interface ModelFactory<in I, out O> {
    fun make(input: I): O
}
