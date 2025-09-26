package com.square.repos.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    val description: String?,
    @ColumnInfo(name = "stargazers_count") val stargazersCount: Int,
    @ColumnInfo(name = "forks_count") val forksCount: Int,
    val language: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    @ColumnInfo(name = "is_bookmarked") val isBookmarked: Boolean = false,
    val page: Int = 1
)