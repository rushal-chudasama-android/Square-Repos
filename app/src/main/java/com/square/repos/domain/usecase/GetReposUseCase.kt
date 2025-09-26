package com.square.repos.domain.usecase

import androidx.paging.PagingData
import com.square.repos.data.local.database.entity.RepoEntity
import com.square.repos.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposUseCase @Inject constructor(
    private val repository: RepoRepository
) {
    operator fun invoke(): Flow<PagingData<RepoEntity>> {
        return repository.getReposStream()
    }
}