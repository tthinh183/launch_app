package com.example.launcher_app.data.repository

import com.example.launcher_app.domain.local.FolderDAO
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.domain.repository.FolderRepository

class FolderRepositoryImpl(
    private val folderDAO: FolderDAO
): FolderRepository {
    override suspend fun insert(folder: Folder) {
        folderDAO.insert(folder)
    }

    override suspend fun getAllFolder(): List<Folder> {
        return folderDAO.getAllForder()
    }

    override suspend fun getFolderByName(name: String): Folder? {
        return folderDAO.getFolderByName(name = name)
    }

    override suspend fun updateFolder(folder: Folder) {
        folderDAO.update(folder)
    }
}