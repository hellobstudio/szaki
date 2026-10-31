package com.szaki.app.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVisitScreen(
    jobId: Long,
    onBack: () -> Unit,
    onSaveVisit: (location: String, notes: String, photoUri: String?, completedOnSite: Boolean, mainWorkDate: String?) -> Unit
) {
    val context = LocalContext.current
    var location by remember { mutableStateOf("GPS: 47.4979, 19.0402") }
    var notes by remember { mutableStateOf("") }
    var completedOnSite by remember { mutableStateOf(false) }
    var mainWorkDate by remember { mutableStateOf("2026-11-10") }
    var capturedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturedPhotoUri = tempPhotoUri
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kiszállás Rögzítése") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Vissza")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Helyszín / GPS koordináta") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Feljegyzés / Tapasztalatok") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = completedOnSite,
                    onCheckedChange = { completedOnSite = it }
                )
                Text("Helyben elvégezve")
            }

            if (!completedOnSite) {
                OutlinedTextField(
                    value = mainWorkDate,
                    onValueChange = { mainWorkDate = it },
                    label = { Text("Fő munkavégzés várható időpontja") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    val photoFile = File(context.externalCacheDir, "photo_${System.currentTimeMillis()}.jpg")
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        photoFile
                    )
                    tempPhotoUri = uri
                    cameraLauncher.launch(uri)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Fotó Készítése (Kamera)")
            }

            capturedPhotoUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Elkészült kép",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onSaveVisit(
                        location,
                        notes,
                        capturedPhotoUri?.toString(),
                        completedOnSite,
                        if (completedOnSite) null else mainWorkDate
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kiszállás Mentése")
            }
        }
    }
}
