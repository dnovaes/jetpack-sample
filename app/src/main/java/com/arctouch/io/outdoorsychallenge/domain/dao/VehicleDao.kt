package com.arctouch.io.outdoorsychallenge.domain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

@Dao
interface VehicleDao {
    @Query("SELECT * from vehicles WHERE isFavorite=1")
    fun getAllFavorites(): List<Vehicle>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: Vehicle)

    @Query("DELETE FROM vehicles")
    suspend fun deleteAll()
}
