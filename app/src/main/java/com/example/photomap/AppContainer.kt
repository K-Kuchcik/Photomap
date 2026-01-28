package com.example.photomap

import android.content.Context
import androidx.room.Room
import com.example.photomap.data.AppDatabase
import com.example.photomap.repo.LocationRepository
import com.example.photomap.repo.PhotoPointRepository

class AppContainer(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "photomap.db"
    ).build()

    private val dao = db.photoPointDao()

    val locationRepository = LocationRepository(context.applicationContext)
    val photoPointRepository = PhotoPointRepository(dao)
}
