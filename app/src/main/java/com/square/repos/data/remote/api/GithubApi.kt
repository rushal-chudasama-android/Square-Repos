package com.square.repos.data.remote.api

import com.square.repos.data.remote.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("orgs/square/repos")
    suspend fun getRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): List<RepoResponse>
}
