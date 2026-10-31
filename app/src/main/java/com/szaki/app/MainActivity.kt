package com.szaki.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.szaki.app.data.AppDatabase
import com.szaki.app.data.JobEntity
import com.szaki.app.data.VisitEntity
import com.szaki.app.ui.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getDatabase(this)
        val dao = db.dao()

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val jobs by dao.getAllJobs().collectAsState(initial = emptyList())

                NavHost(navController = navController, startDestination = "job_list") {
                    composable("job_list") {
                        JobListScreen(
                            jobs = jobs,
                            onAddJobClick = { navController.navigate("add_job") },
                            onJobClick = { jobId -> navController.navigate("job_detail/$jobId") },
                            onCalendarClick = { navController.navigate("calendar") }
                        )
                    }

                    composable("add_job") {
                        AddJobScreen(
                            onBack = { navController.popBackStack() },
                            onSave = { clientName, address, task, date, time ->
                                coroutineScope.launch {
                                    dao.insertJob(
                                        JobEntity(
                                            clientName = clientName,
                                            address = address,
                                            taskDescription = task,
                                            date = date,
                                            time = time
                                        )
                                    )
                                    navController.popBackStack()
                                }
                            }
                        )
                    }

                    composable(
                        "job_detail/{jobId}",
                        arguments = listOf(navArgument("jobId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L
                        val selectedJob = jobs.find { it.id == jobId }
                        val visits by dao.getVisitsForJob(jobId).collectAsState(initial = emptyList())

                        JobDetailScreen(
                            job = selectedJob,
                            visits = visits,
                            onBack = { navController.popBackStack() },
                            onAddVisitClick = { navController.navigate("add_visit/$jobId") }
                        )
                    }

                    composable(
                        "add_visit/{jobId}",
                        arguments = listOf(navArgument("jobId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val jobId = backStackEntry.arguments?.getLong("jobId") ?: 0L
                        AddVisitScreen(
                            jobId = jobId,
                            onBack = { navController.popBackStack() },
                            onSaveVisit = { location, notes, photoUri, completedOnSite, mainWorkDate ->
                                coroutineScope.launch {
                                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                                    dao.insertVisit(
                                        VisitEntity(
                                            jobId = jobId,
                                            date = today,
                                            location = location,
                                            notes = notes,
                                            photoUri = photoUri,
                                            completedOnSite = completedOnSite,
                                            mainWorkDate = mainWorkDate
                                        )
                                    )
                                    if (completedOnSite) {
                                        dao.getJobById(jobId)?.let { currentJob ->
                                            dao.updateJob(currentJob.copy(status = "Elvégezve"))
                                        }
                                    }
                                    navController.popBackStack()
                                }
                            }
                        )
                    }

                    composable("calendar") {
                        CalendarScreen(
                            jobs = jobs,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
