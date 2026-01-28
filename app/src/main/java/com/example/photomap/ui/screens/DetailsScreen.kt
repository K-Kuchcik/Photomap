package com.example.photomap.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.photomap.ui.viewmodel.DetailsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBack: () -> Unit
) {
    val item = viewModel.item.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Wstecz")
                    }
                },
                actions = {
                    if (item != null) {
                        IconButton(onClick = {
                            viewModel.delete(item)
                            onBack()
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Usuń")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (item == null) {
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("Nie znaleziono wpisu.")
            }
            return@Scaffold
        }

        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date(item.createdAtMillis))

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = item.photoUri,
                contentDescription = "Zdjęcie",
                modifier = Modifier.fillMaxWidth()
            )

            Text("ID: ${item.id}")
            Text("Data zapisu: $date")
            Text("GPS: ${item.lat}, ${item.lng}")
            Text("EXIF:")
            Text("• DateTime: ${item.exifDateTime ?: "-"}")
            Text("• Make: ${item.exifMake ?: "-"}")
            Text("• Model: ${item.exifModel ?: "-"}")
            Text("• Orientation: ${item.exifOrientation ?: "-"}")

            Button(onClick = {
                viewModel.delete(item)
                onBack()
            }) {
                Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(" Usuń wpis")
            }
        }
    }
}
