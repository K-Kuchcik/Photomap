package com.example.photomap.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photomap.data.PhotoPointEntity
import com.example.photomap.repo.PhotoPointRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val photoRepo: PhotoPointRepository,
    private val id: Long
) : ViewModel() {

    val item: StateFlow<PhotoPointEntity?> = photoRepo.observeById(id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun delete(entity: PhotoPointEntity) {
        viewModelScope.launch { photoRepo.delete(entity) }
    }
}
