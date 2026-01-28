package com.example.photomap.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoPointDao {

    @Query("SELECT * FROM photo_points ORDER BY createdAtMillis DESC")
    fun observeAll(): Flow<List<PhotoPointEntity>>

    @Query("SELECT * FROM photo_points WHERE id = :id")
    fun observeById(id: Long): Flow<PhotoPointEntity?>

    @Insert
    suspend fun insert(item: PhotoPointEntity): Long

    @Delete
    suspend fun delete(item: PhotoPointEntity)

    @Query("DELETE FROM photo_points")
    suspend fun deleteAll()
}
