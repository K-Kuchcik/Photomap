package com.example.photomap.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

object ImageUriFactory {

    fun createImageUri(context: Context): Uri {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "photomap_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        ) ?: throw IllegalStateException("Nie udało się utworzyć URI w MediaStore")
    }
}
