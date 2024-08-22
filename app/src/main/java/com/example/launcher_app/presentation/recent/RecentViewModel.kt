package com.example.launcher_app.presentation.recent

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.launcher_app.domain.model.TaskInfo
import com.example.launcher_app.domain.repository.InstalledAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    private val installedAppRepository: InstalledAppRepository,
    @ApplicationContext private val context: Context
): ViewModel(){

    private val _recentAppList = MutableStateFlow<List<TaskInfo>>(emptyList())
    val recentAppList: StateFlow<List<TaskInfo>> = _recentAppList

    fun requestUsageStatsPermission(activity: Activity) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun hasUsageStatsPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.packageName)
            return mode == AppOpsManager.MODE_ALLOWED
        }
        return false
    }

    fun getRecentApp() {
        viewModelScope.launch {
            installedAppRepository.fetchRecentAppList().collect {
                data ->
                _recentAppList.value = data
            }
        }
    }
}