package com.lvm.registrador.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.lvm.registrador.data.model.Medidor
import com.lvm.registrador.data.db.dao.MedidorDao

@Database(entities = [Medidor::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun medidorDao(): MedidorDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medidor_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
