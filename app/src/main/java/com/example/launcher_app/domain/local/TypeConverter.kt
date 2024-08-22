package com.example.launcher_app.domain.local

import androidx.room.ProvidedTypeConverter
import com.example.launcher_app.domain.model.TaskInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.room.TypeConverter

class TypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromTaskInfoList(value: List<TaskInfo>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTaskInfoList(value: String?): List<TaskInfo>? {
        val listType = object : TypeToken<List<TaskInfo>>() {}.type
        return gson.fromJson(value, listType)
    }
}