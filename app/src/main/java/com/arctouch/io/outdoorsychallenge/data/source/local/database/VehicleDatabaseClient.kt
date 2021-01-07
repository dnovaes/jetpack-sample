package com.arctouch.io.outdoorsychallenge.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arctouch.io.outdoorsychallenge.data.source.local.database.dao.VehicleDao
import com.arctouch.io.outdoorsychallenge.data.source.local.database.entity.VehicleEntity
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle

@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class VehicleDatabaseClient : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    companion object {
        private const val TABLE_NAME = "outdoorsy_database"

        fun setupDatabase(context: Context) = Room.databaseBuilder(
            context,
            VehicleDatabaseClient::class.java,
            TABLE_NAME
        ).build()
    }
}
