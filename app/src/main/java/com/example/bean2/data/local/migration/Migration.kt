package com.example.bean2.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a new table with the updated schema
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `coffee_bags_new` (
                `id` TEXT NOT NULL, 
                `name` TEXT NOT NULL, 
                `roaster` TEXT NOT NULL, 
                `type` TEXT NOT NULL, 
                `brewingParams` TEXT, 
                `notes` TEXT NOT NULL, 
                `createdAt` INTEGER NOT NULL, 
                `updatedAt` INTEGER NOT NULL, 
                PRIMARY KEY(`id`)
            )
        """.trimIndent())
        
        // Copy data from old table to new table
        database.execSQL("""
            INSERT INTO `coffee_bags_new` 
            (id, name, roaster, type, notes, createdAt, updatedAt)
            SELECT id, name, roaster, type, '', createdAt, updatedAt 
            FROM `coffee_bags`
        """.trimIndent())
        
        // Drop the old table
        database.execSQL("DROP TABLE `coffee_bags`")
        
        // Rename the new table to the original name
        database.execSQL("ALTER TABLE `coffee_bags_new` RENAME TO `coffee_bags`")
    }

}
