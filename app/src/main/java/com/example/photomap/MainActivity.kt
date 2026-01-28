package com.example.photomap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.photomap.ui.navigation.AppNavHost
import com.example.photomap.ui.theme.PhotoMapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as PhotoMapApp

        setContent {
            PhotoMapTheme {
                AppNavHost(appContainer = app.container)
            }
        }
    }
}
