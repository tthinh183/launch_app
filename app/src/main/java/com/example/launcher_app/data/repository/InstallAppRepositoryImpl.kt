package com.example.launcher_app.data.repository

import android.app.usage.UsageStats
import android.content.Context
import android.content.Intent
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.domain.repository.InstalledAppRepository
import com.example.launcher_app.util.AppUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InstallAppRepositoryImpl @Inject constructor(
    private val context: Context
) : InstalledAppRepository{
    override suspend fun fetchInstalledAppList(): Flow<List<TaskInfo>> {
        return AppUtils.getInstalledApp(context = context)
    }

    override suspend fun fetchRecentAppList(): Flow<List<TaskInfo>> {
        return AppUtils.fetchRecentUsedApp(context = context)
    }
}