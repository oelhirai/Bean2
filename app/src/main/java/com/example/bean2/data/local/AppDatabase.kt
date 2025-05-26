package com.example.bean2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bean2.data.local.converter.BrewingParamsConverter
import com.example.bean2.data.local.converter.CoffeeTypeConverter
import com.example.bean2.data.local.dao.CoffeeBagDao
import com.example.bean2.data.local.entity.CoffeeBagEntity
import com.example.bean2.data.local.migration.MIGRATION_1_2

@Database(
    entities = [CoffeeBagEntity::class],
    version = 2,  // Incremented version for schema changes
    exportSchema = true
)
@TypeConverters(CoffeeTypeConverter::class, BrewingParamsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeBagDao(): CoffeeBagDao
    
    companion object {
        const val DATABASE_NAME = "bean2_database"
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration() // Only for development
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
