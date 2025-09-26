package com.square.repos.domain.usecase

import com.square.repos.domain.repository.RepoRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: RepoRepository
) {
    suspend operator fun invoke(repoId: Long) {
        repository.toggleBookmark(repoId)
    }
}