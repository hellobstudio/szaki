package com.szaki.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.szaki.app.data.JobEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    jobs: List<JobEntity>,
    onBack: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("2026-11-01") }

    val filteredJobs = jobs.filter { it.date == selectedDate }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Naptár - Napi Munkák") },
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Kiválasztott dátum (ÉÉÉÉ-HH-NN)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ezen a napon esedékes munkák:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            if (filteredJobs.isEmpty()) {
                Text("Ezen a napon nincs ütemezett feladat.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredJobs) { job ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = "${job.time} - ${job.clientName}", style = MaterialTheme.typography.titleSmall)
                                Text(text = "Cím: ${job.address}")
                                Text(text = "Feladat: ${job.taskDescription}")
                            }
                        }
                    }
                }
            }
        }
    }
}
