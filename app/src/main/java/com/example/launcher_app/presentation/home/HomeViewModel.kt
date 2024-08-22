package com.example.launcher_app.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.domain.repository.InstalledAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val listInstalledApp: InstalledAppRepository
): ViewModel(){

    private val _listApp = MutableStateFlow<List<TaskInfo>>(emptyList())
    val listApp: StateFlow<List<TaskInfo>> = _listApp.asStateFlow()

    init {
        getListApp()
        Log.d("_listApp", "${_listApp.value.size}")
    }

    private fun getListApp() {
        viewModelScope.launch {
            listInstalledApp.fetchInstalledAppList().collect {
                data ->
                _listApp.value = data
            }
        }
    }
}