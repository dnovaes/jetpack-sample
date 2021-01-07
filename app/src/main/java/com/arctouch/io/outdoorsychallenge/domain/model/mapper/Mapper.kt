package com.arctouch.io.outdoorsychallenge.domain.model.mapper

interface Mapper<E, D> {
    fun toDomain(entity: E): D

    fun fromDomain(domain: D): E
}