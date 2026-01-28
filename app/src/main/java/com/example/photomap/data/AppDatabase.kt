package com.example.photomap.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PhotoPointEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoPointDao(): PhotoPointDao
}
