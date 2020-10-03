package com.arctouch.io.outdoorsychallenge.domain.model

data class Vehicle(
    val id: String,
    val name: String?,
    val imageUrl: String?,
    val isFavorite: Boolean = false
)
