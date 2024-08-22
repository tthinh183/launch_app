package com.example.launcher_app.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.example.launcher_app.domain.model.TaskInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AppUtils {
    fun getInstalledApp(context: Context): Flow<List<TaskInfo>> {
        return flow {
            val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val resolveInfoList = context.packageManager.queryIntentActivities(mainIntent, 0)
            val taskInfoList = mutableListOf<TaskInfo>()
            for (resolveInfo in resolveInfoList) {
                if (resolveInfo.activityInfo.packageName != context.packageName
                    && resolveInfo.activityInfo.packageName != "com.android.settings"
                ) {
                    val appInfo = context.packageManager.getApplicationInfo(
                        resolveInfo.activityInfo.packageName,
                        0
                    )
                    val taskInfo = TaskInfo(
                        appInfo = appInfo,
                    )
                    if (!taskInfoList.contains(taskInfo)) {
                        taskInfoList.add(taskInfo)
                    }
                }
            }
            emit(taskInfoList)
        }
    }

    fun fetchRecentUsedApp(context: Context): Flow<List<TaskInfo>> {
        return flow {
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val currentTime = System.currentTimeMillis()
            // Lấy dữ liệu từ 24 giờ trước cho đến hiện tại
            val startTime = currentTime - 24 * 60 * 60 * 1000
            val usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                currentTime
            )
            val recentUsedApps = usageStatsList.filter { it.lastTimeUsed > 0 }
            val taskInfoList = mutableListOf<TaskInfo>()
            for (usageStats in recentUsedApps) {
                val packageName = usageStats.packageName
                try {
                    val appInfo = context.packageManager.getApplicationInfo(packageName, 0)
                    taskInfoList.add(TaskInfo(appInfo = appInfo))
                } catch (e: PackageManager.NameNotFoundException) {
                    Log.e("FetchRecentUsedApp", "App not found: $packageName", e)
                }
            }
            emit(taskInfoList)
        }
    }
}