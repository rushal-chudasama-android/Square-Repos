package com.square.repos.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.square.repos.data.local.database.dao.RepoDao
import com.square.repos.data.local.database.entity.RepoEntity
import com.square.repos.data.remote.api.GithubApi

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val githubApi: GithubApi,
    private val repoDao: RepoDao
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.page?.plus(1) ?: 1
                }
            }

            val response = githubApi.getRepos(page = page)
            val repos = response.map { it.toRepoEntity(page = page) }

            if (loadType == LoadType.REFRESH) {
                repoDao.clearAll()
            }

            repoDao.insertRepos(repos)

            MediatorResult.Success(
                endOfPaginationReached = repos.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}