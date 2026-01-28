package com.example.photomap.util

import android.Manifest
import android.os.Build

object PermissionUtils {

    val requiredPermissions: Array<String>
        get() {
            val base = mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (Build.VERSION.SDK_INT >= 33) {
                base.add(Manifest.permission.READ_MEDIA_IMAGES)
            }
            return base.toTypedArray()
        }
}
