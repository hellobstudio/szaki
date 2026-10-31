package com.szaki.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.szaki.app.data.JobEntity
import com.szaki.app.data.VisitEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: JobEntity?,
    visits: List<VisitEntity>,
    onBack: () -> Unit,
    onAddVisitClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(job?.clientName ?: "Munka Részletei") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Vissza")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddVisitClick) {
                Icon(Icons.Default.Add, contentDescription = "Új kiszállás")
            }
        }
    ) { padding ->
        if (job != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Cím: ${job.address}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "Feladat: ${job.taskDescription}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Tervezett időpont: ${job.date} ${job.time}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Kiszállások és Helyszíni Felmérések", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(visits) { visit ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = "Dátum: ${visit.date}", style = MaterialTheme.typography.labelLarge)
                                Text(text = "Helyzet/GPS: ${visit.location}")
                                Text(text = "Megjegyzés: ${visit.notes}")
                                if (visit.completedOnSite) {
                                    Text(text = " Állapot: Helyben elvégezve", color = MaterialTheme.colorScheme.primary)
                                } else {
                                    Text(text = " Fő munkavégzés időpontja: ${visit.mainWorkDate ?: "Nincs megadva"}")
                                }
                                visit.photoUri?.let { uri ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Helyszíni fotó",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
