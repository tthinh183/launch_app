package com.example.launcher_app.di

import android.app.Application
import androidx.room.Room
import com.example.launcher_app.data.repository.FolderRepositoryImpl
import com.example.launcher_app.data.repository.InstallAppRepositoryImpl
import com.example.launcher_app.domain.local.FolderDAO
import com.example.launcher_app.domain.local.FolderDatabase
import com.example.launcher_app.domain.local.TypeConverter
import com.example.launcher_app.domain.model.Folder
import com.example.launcher_app.domain.repository.FolderRepository
import com.example.launcher_app.domain.repository.InstalledAppRepository
import com.example.launcher_app.helper.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInstalledAppList(
        application: Application
    ): InstalledAppRepository {
        return InstallAppRepositoryImpl(application)
    }

    @Provides
    @Singleton
    fun provideFolderDatabase(
        application: Application
    ): FolderDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = FolderDatabase::class.java,
            name = Constant.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFolderDao(
        folderDatabase: FolderDatabase
    ): FolderDAO = folderDatabase.folderDAO

    @Provides
    @Singleton
    fun provideFolderRepository(
        folderDAO: FolderDAO
    ): FolderRepository {
        return FolderRepositoryImpl(folderDAO = folderDAO)
    }
}