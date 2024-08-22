package com.example.launcher_app.presentation.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.domain.repository.FolderRepository
import com.example.launcher_app.domain.repository.InstalledAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import javax.inject.Inject

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    private val repository: FolderRepository,
    private val installedAppRepository: InstalledAppRepository
): ViewModel(){

    private val _listApp = MutableStateFlow<List<TaskInfo>>(emptyList())
    val listApp: StateFlow<List<TaskInfo>> = _listApp

    private val _appsOfFolder = MutableStateFlow<List<TaskInfo>>(emptyList())
    val appsOfFolder: StateFlow<List<TaskInfo>> = _appsOfFolder

    fun getAllApp() {
        viewModelScope.launch {
            installedAppRepository.fetchInstalledAppList().collect {
                data ->
                _listApp.value = data
            }
        }
    }

    fun getListAppOfFolder(folderName: String) {
        viewModelScope.launch {
            try {
                val folder = repository.getFolderByName(folderName)
                val apps = folder?.let { folder.getAppOfFolder() } ?: emptyList()
                _appsOfFolder.value = apps
            } catch (e: Exception) {
                e.printStackTrace()
                _appsOfFolder.value = emptyList()
            }
        }
    }

    fun updateFolder(folder: Folder) {
        viewModelScope.launch {
            repository.updateFolder(folder)
            getListAppOfFolder(folderName = folder.name)
        }
    }
}