package com.square.repos.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.square.repos.data.local.database.dao.RepoDao
import com.square.repos.data.local.database.entity.RepoEntity
import com.square.repos.data.remote.api.GithubApi
import com.square.repos.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepositoryImpl @Inject constructor(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao
) : RepoRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getReposStream(): Flow<PagingData<RepoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = GithubRemoteMediator(githubApi, repoDao),
            pagingSourceFactory = { repoDao.getPagingSource() }
        ).flow
    }

    override fun getBookmarksStream(): Flow<List<RepoEntity>> {
        return repoDao.observeBookmarks()
    }

    override fun getRepoStream(repoId: Long): Flow<RepoEntity?> {
        return repoDao.observeRepoById(repoId)
    }

    override suspend fun toggleBookmark(repoId: Long) {
        val currentRepo = repoDao.observeRepoById(repoId).first()
        currentRepo?.let { repo ->
            repoDao.updateBookmarkStatus(repoId, !repo.isBookmarked)
        }
    }

    override suspend fun refreshRepos() {
        repoDao.clearAll()
    }
}
