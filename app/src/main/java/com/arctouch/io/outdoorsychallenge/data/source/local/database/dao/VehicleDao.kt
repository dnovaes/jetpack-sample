package com.arctouch.io.outdoorsychallenge.data.source.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arctouch.io.outdoorsychallenge.data.source.local.database.entity.VehicleEntity

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vehicle: VehicleEntity)

    @Query("DELETE FROM vehicle WHERE id = :id")
    suspend fun deleteBy(id: String)

    @Query("DELETE FROM vehicle")
    suspend fun deleteAll()

    @Query("SELECT * from vehicle")
    suspend fun getAll(): List<VehicleEntity>

    @Query("SELECT * FROM vehicle WHERE id = :id")
    suspend fun getBy(id: String): VehicleEntity?

    @Query("SELECT * FROM vehicle WHERE id = :id")
    fun observeBy(id: String): LiveData<VehicleEntity?>
}
