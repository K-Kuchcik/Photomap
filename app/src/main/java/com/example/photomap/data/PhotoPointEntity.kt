package com.example.photomap.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_points")
data class PhotoPointEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val photoUri: String,
    val lat: Double,
    val lng: Double,
    val createdAtMillis: Long,
    val exifDateTime: String?,
    val exifMake: String?,
    val exifModel: String?,
    val exifOrientation: Int?
)
