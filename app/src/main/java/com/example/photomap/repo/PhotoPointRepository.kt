package com.example.photomap.repo

import com.example.photomap.data.PhotoPointDao
import com.example.photomap.data.PhotoPointEntity
import kotlinx.coroutines.flow.Flow

class PhotoPointRepository(
    private val dao: PhotoPointDao
) {
    fun observeAll(): Flow<List<PhotoPointEntity>> = dao.observeAll()
    fun observeById(id: Long): Flow<PhotoPointEntity?> = dao.observeById(id)

    suspend fun insert(entity: PhotoPointEntity): Long = dao.insert(entity)
    suspend fun delete(entity: PhotoPointEntity) = dao.delete(entity)
    suspend fun deleteAll() = dao.deleteAll()
}
