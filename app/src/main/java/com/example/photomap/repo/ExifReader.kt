package com.example.photomap.repo

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

data class ExifData(
    val dateTime: String?,
    val make: String?,
    val model: String?,
    val orientation: Int?
)

object ExifReader {

    fun read(context: Context, uri: Uri): ExifData {
        val resolver = context.contentResolver
        resolver.openInputStream(uri).use { input ->
            if (input == null) return ExifData(null, null, null, null)

            val exif = ExifInterface(input)
            return ExifData(
                dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME),
                make = exif.getAttribute(ExifInterface.TAG_MAKE),
                model = exif.getAttribute(ExifInterface.TAG_MODEL),
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            )
        }
    }
}
