package com.square.repos.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.square.repos.core.network.NetworkMonitor
import com.square.repos.data.local.database.RepoDatabase
import com.square.repos.data.local.database.dao.RepoDao
import com.square.repos.data.local.datastore.PreferencesManager
import com.square.repos.data.remote.api.GithubApi
import com.square.repos.data.repository.RepoRepositoryImpl
import com.square.repos.domain.repository.RepoRepository
import com.square.repos.domain.usecase.GetBookmarksUseCase
import com.square.repos.domain.usecase.GetRepoUseCase
import com.square.repos.domain.usecase.GetReposUseCase
import com.square.repos.domain.usecase.ToggleBookmarkUseCase
import com.square.repos.presentation.settings.SettingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRepoDatabase(@ApplicationContext context: Context): RepoDatabase {
        return RepoDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideRepoDao(database: RepoDatabase): RepoDao {
        return database.repoDao()
    }

    @Provides
    @Singleton
    fun provideRepoRepository(
        githubApi: GithubApi,
        repoDao: RepoDao
    ): RepoRepository {
        return RepoRepositoryImpl(githubApi, repoDao)
    }

    @Provides
    @Singleton
    fun provideGetReposUseCase(repository: RepoRepository): GetReposUseCase {
        return GetReposUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBookmarksUseCase(repository: RepoRepository): GetBookmarksUseCase {
        return GetBookmarksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleBookmarkUseCase(repository: RepoRepository): ToggleBookmarkUseCase {
        return ToggleBookmarkUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRepoUseCase(repository: RepoRepository): GetRepoUseCase {
        return GetRepoUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }

    @Provides
    @Singleton
    fun provideSettingsViewModelFactory(
        preferencesManager: PreferencesManager
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(preferencesManager) as T
            }
        }
    }
}