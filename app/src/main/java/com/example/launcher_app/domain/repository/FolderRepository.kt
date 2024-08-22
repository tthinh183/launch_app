package com.example.launcher_app.domain.repository

import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.model.TaskInfo

interface FolderRepository {
    suspend fun insert(folder: Folder)
    suspend fun getAllFolder(): List<Folder>
    suspend fun getFolderByName(name: String): Folder?
    suspend fun updateFolder(folder: Folder)
}