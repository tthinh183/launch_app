package com.example.launcher_app.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "folders"
)
data class Folder(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "apps")
    var apps: List<TaskInfo>
) : Serializable {
    fun getAppOfFolder(): List<TaskInfo> {
        return apps
    }
}
