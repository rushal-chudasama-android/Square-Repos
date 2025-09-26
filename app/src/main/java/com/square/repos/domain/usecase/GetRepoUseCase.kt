package com.square.repos.domain.usecase

import com.square.repos.data.local.database.entity.RepoEntity
import com.square.repos.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(
    private val repository: RepoRepository
) {
    operator fun invoke(repoId: Long): Flow<RepoEntity?> {
        return repository.getRepoStream(repoId)
    }
}