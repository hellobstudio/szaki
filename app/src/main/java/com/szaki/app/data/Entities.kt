package com.szaki.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class JobEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val address: String,
    val taskDescription: String,
    val date: String, // YYYY-MM-DD
    val time: String, // HH:MM
    val status: String = "Tervezett" // Tervezett, Folyamatban, Elvégezve
)

@Entity(tableName = "visits")
data class VisitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val jobId: Long,
    val date: String,
    val location: String, // GPS koordináta vagy megnevezés
    val notes: String,
    val photoUri: String?,
    val completedOnSite: Boolean,
    val mainWorkDate: String? // Ha nem helyben végezte el, a fő munkavégzés tervezett időpontja
)
