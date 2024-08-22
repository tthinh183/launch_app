package com.example.launcher_app.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.model.TaskInfo

@Dao
interface FolderDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: Folder)

    @Query("SELECT * FROM folders")
    suspend fun getAllForder(): List<Folder>

    @Query("SELECT * FROM folders WHERE name =:name")
    suspend fun getFolderByName(name: String): Folder?

    @Update
    suspend fun update(folder: Folder)
}