package com.example.photomap.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photomap.repo.LocationRepository
import com.example.photomap.repo.PhotoPointRepository

class ViewModelFactories(
    private val appContext: Context,
    private val photoRepo: PhotoPointRepository,
    private val locationRepo: LocationRepository
) {

    val mapFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MapViewModel(
                appContext = appContext,
                photoRepo = photoRepo,
                locationRepo = locationRepo
            ) as T
        }
    }

    fun detailsFactory(id: Long): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(
                photoRepo = photoRepo,
                id = id
            ) as T
        }
    }
}
