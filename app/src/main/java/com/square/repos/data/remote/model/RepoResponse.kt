package com.square.repos.data.remote.model

import com.square.repos.data.local.database.entity.RepoEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "description") val description: String?,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "forks_count") val forksCount: Int,
    @Json(name = "language") val language: String?,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "owner") val owner: OwnerResponse
) {
    fun toRepoEntity(page: Int = 1) = RepoEntity(
        id = id,
        name = name,
        fullName = fullName,
        description = description,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        language = language,
        updatedAt = updatedAt,
        htmlUrl = htmlUrl,
        page = page
    )
}

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)