package com.arctouch.io.outdoorsychallenge.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?
)