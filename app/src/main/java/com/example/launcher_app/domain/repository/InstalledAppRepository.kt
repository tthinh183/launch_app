package com.example.launcher_app.domain.repository

import android.app.usage.UsageStats
import android.content.Context
import com.example.launcher_app.domain.model.TaskInfo
import kotlinx.coroutines.flow.Flow

interface InstalledAppRepository {
    suspend fun fetchInstalledAppList(): Flow<List<TaskInfo>>
    suspend fun fetchRecentAppList(): Flow<List<TaskInfo>>
}