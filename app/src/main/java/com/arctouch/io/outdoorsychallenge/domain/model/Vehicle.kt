package com.arctouch.io.outdoorsychallenge.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String?,
    val imageUrl: String?,
    val isFavorite: Boolean = false
)
