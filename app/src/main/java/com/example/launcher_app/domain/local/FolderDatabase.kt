package com.example.launcher_app.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.helper.Constant

@Database(
    entities = [Folder::class],
    version = Constant.DATABASE_VERSION
)
@TypeConverters(TypeConverter::class)
abstract class FolderDatabase : RoomDatabase(){
    abstract val folderDAO: FolderDAO
}