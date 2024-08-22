package com.example.launcher_app.presentation.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.repository.FolderRepository
import com.example.launcher_app.domain.repository.InstalledAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(
    private val repository: FolderRepository,
    private val listInstalledApp: InstalledAppRepository
): ViewModel(){

    private val _listFolder = MutableStateFlow<List<Folder>>(emptyList())
    val listFolder: StateFlow<List<Folder>> = _listFolder

    fun insertFolder(folderName: String) {
        viewModelScope.launch {
            val existingFolder = repository.getFolderByName(name = folderName)
            if (existingFolder == null) {
                val newFolder = Folder(name = folderName, apps = emptyList())
                repository.insert(newFolder)
                getAllFolder()
            }
        }
    }

    fun getAllFolder() {
        viewModelScope.launch {
            _listFolder.value = repository.getAllFolder()
        }
    }

}