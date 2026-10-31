package com.szaki.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(
    onBack: () -> Unit,
    onSave: (clientName: String, address: String, task: String, date: String, time: String) -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("2026-11-01") }
    var time by remember { mutableStateOf("08:00") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Új Munka Felvétele") },
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
                value = clientName,
                onValueChange = { clientName = it },
                label = { Text("Ügyfél neve (Kinél?)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Lakcím") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = task,
                onValueChange = { task = it },
                label = { Text("Feladat megnevezése") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Dátum (ÉÉÉÉ-HH-NN)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Időpont (ÓÓ:PP)") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (clientName.isNotBlank() && address.isNotBlank()) {
                        onSave(clientName, address, task, date, time)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Munka Mentése")
            }
        }
    }
}
