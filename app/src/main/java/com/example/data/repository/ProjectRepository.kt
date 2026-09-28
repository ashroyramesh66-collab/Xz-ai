package com.example.data.repository

import com.example.data.local.ProjectDao
import com.example.data.model.SavedProject
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<SavedProject>> = projectDao.getAllProjects()

    suspend fun insertProject(project: SavedProject): Long {
        return projectDao.insertProject(project)
    }

    suspend fun deleteProjectById(id: Int) {
        projectDao.deleteProjectById(id)
    }

    suspend fun clearAll() {
        projectDao.clearAll()
    }
}
