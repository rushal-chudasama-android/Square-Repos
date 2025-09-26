package com.square.repos.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.square.repos.data.local.database.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM repositories ORDER BY stargazers_count DESC")
    fun observeAllRepos(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repositories WHERE is_bookmarked = 1 ORDER BY stargazers_count DESC")
    fun observeBookmarks(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repositories WHERE id = :repoId")
    fun observeRepoById(repoId: Long): Flow<RepoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Update
    suspend fun updateRepo(repo: RepoEntity)

    @Query("UPDATE repositories SET is_bookmarked = :isBookmarked WHERE id = :repoId")
    suspend fun updateBookmarkStatus(repoId: Long, isBookmarked: Boolean)

    @Query("DELETE FROM repositories")
    suspend fun clearAll()

    @Query("SELECT * FROM repositories ORDER BY stargazers_count DESC")
    fun getPagingSource(): PagingSource<Int, RepoEntity>
}