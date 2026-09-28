package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_projects")
data class SavedProject(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val appType: String,
    val architecture: String,
    val selectedFeatures: String,
    val themeName: String,
    val fontPairing: String,
    val targetPlatform: String,
    val generatedPrompt: String,
    val createdAt: Long = System.currentTimeMillis()
)
