package com.example.photomap.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photomap.data.PhotoPointEntity
import com.example.photomap.repo.ExifReader
import com.example.photomap.repo.LocationRepository
import com.example.photomap.repo.PhotoPointRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MapUiState(
    val items: List<PhotoPointEntity> = emptyList(),
    val error: String? = null
)

class MapViewModel(
    private val appContext: Context,
    private val photoRepo: PhotoPointRepository,
    private val locationRepo: LocationRepository
) : ViewModel() {

    val uiState: StateFlow<MapUiState> = photoRepo.observeAll()
        .map { MapUiState(items = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MapUiState())

    fun onPhotoCaptured(uri: Uri) {
        viewModelScope.launch {
            val loc = locationRepo.getCurrentLocation()
            if (loc == null) return@launch

            val exif = ExifReader.read(appContext, uri)

            photoRepo.insert(
                PhotoPointEntity(
                    photoUri = uri.toString(),
                    lat = loc.latitude,
                    lng = loc.longitude,
                    createdAtMillis = System.currentTimeMillis(),
                    exifDateTime = exif.dateTime,
                    exifMake = exif.make,
                    exifModel = exif.model,
                    exifOrientation = exif.orientation
                )
            )
        }
    }

    fun deleteAll() {
        viewModelScope.launch { photoRepo.deleteAll() }
    }
}
