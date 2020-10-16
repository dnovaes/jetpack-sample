package com.arctouch.io.outdoorsychallenge.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arctouch.io.outdoorsychallenge.domain.dao.VehicleDao
import com.arctouch.io.outdoorsychallenge.domain.model.Vehicle
import kotlinx.coroutines.CoroutineScope

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Vehicle::class], version = 1, exportSchema = false)
abstract class VehicleRoomDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VehicleRoomDatabase? = null
        const val TABLE_NAME = "vehicles_database"

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): VehicleRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleRoomDatabase::class.java,
                    TABLE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
