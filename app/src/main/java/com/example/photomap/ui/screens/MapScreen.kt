package com.example.photomap.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.photomap.ui.viewmodel.MapViewModel
import com.example.photomap.util.ImageUriFactory
import com.example.photomap.util.PermissionUtils
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    onOpenDetails: (Long) -> Unit
) {
    val context = LocalContext.current
    val appCtx = context.applicationContext
    val state = viewModel.uiState.collectAsState().value

    DisposableEffect(Unit) {
        setupOsmdroid(appCtx)
        onDispose { }
    }

    fun hasAllPermissions(): Boolean =
        PermissionUtils.requiredPermissions.all { perm ->
            ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { /* wynik niepotrzebny, sprawdzamy hasAllPermissions() */ }

    var pendingUri: Uri? by remember { mutableStateOf(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        val uri = pendingUri
        if (success && uri != null) {
            viewModel.onPhotoCaptured(uri)
        }
    }

    fun startCapture() {
        if (!hasAllPermissions()) {
            permissionsLauncher.launch(PermissionUtils.requiredPermissions)
            return
        }

        val uri = ImageUriFactory.createImageUri(context)
        pendingUri = uri
        takePictureLauncher.launch(uri)
    }

    LaunchedEffect(Unit) {
        if (!hasAllPermissions()) {
            permissionsLauncher.launch(PermissionUtils.requiredPermissions)
        }
    }

    val mapView = remember {
        MapView(appCtx).apply {
            setMultiTouchControls(true)
            controller.setZoom(5.0)
            controller.setCenter(GeoPoint(52.0, 19.0)) // start: Polska
        }
    }

    LaunchedEffect(state.items) {
        mapView.overlays.removeAll { it is Marker }

        state.items.forEach { item ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(item.lat, item.lng)
                title = "Zdjęcie #${item.id}"
                setOnMarkerClickListener { _, _ ->
                    onOpenDetails(item.id)
                    true
                }
            }
            mapView.overlays.add(marker)
        }

        state.items.firstOrNull()?.let { first ->
            mapView.controller.setZoom(12.0)
            mapView.controller.setCenter(GeoPoint(first.lat, first.lng))
        }

        mapView.invalidate()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PhotoMap (OSM)") },
                actions = {
                    IconButton(onClick = { viewModel.deleteAll() }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Usuń wszystko")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { startCapture() }) {
                Icon(Icons.Filled.Add, contentDescription = "Dodaj zdjęcie")
            }
        }
    ) { padding ->
        if (!hasAllPermissions()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Nadaj uprawnienia: Kamera + Lokalizacja.")
                Text("Kliknij + i zaakceptuj prośby o uprawnienia.")
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                factory = { mapView }
            )
        }
    }
}

private fun setupOsmdroid(context: Context) {
    Configuration.getInstance().userAgentValue = context.packageName
}
