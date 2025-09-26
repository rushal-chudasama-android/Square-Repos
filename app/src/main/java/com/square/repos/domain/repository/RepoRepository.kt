package com.square.repos.domain.repository

import androidx.paging.PagingData
import com.square.repos.data.local.database.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getReposStream(): Flow<PagingData<RepoEntity>>
    fun getBookmarksStream(): Flow<List<RepoEntity>>
    fun getRepoStream(repoId: Long): Flow<RepoEntity?>
    suspend fun toggleBookmark(repoId: Long)
    suspend fun refreshRepos()
}