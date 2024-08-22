package com.example.launcher_app.domain.model

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
data class TaskInfo(
    var appInfo: ApplicationInfo? = null,
) {
    fun getIcon(pm: PackageManager): ImageBitmap? {
        return appInfo?.let {
            val icon = it.loadIcon(pm)
            icon.toBitmap().asImageBitmap()
        }
    }

    fun getLabel(pm: PackageManager): String {
        return appInfo?.loadLabel(pm)?.toString() ?: "Unknown"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskInfo

        return appInfo?.packageName == other.appInfo?.packageName
    }

    override fun hashCode(): Int {
        return appInfo?.packageName?.hashCode() ?: 0
    }
}
