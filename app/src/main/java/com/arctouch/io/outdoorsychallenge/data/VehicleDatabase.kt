package com.arctouch.io.outdoorsychallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arctouch.io.outdoorsychallenge.domain.dao.VehicleDao
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Vehicle::class], version = 1, exportSchema = false)
abstract class VehicleDatabase : RoomDatabase() {

    companion object {
        const val TABLE_NAME = "vehicles_database"
    }

    abstract fun vehicleDao(): VehicleDao
}
