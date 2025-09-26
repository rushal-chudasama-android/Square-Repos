package com.square.repos.presentation.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.square.repos.domain.usecase.GetBookmarksUseCase
import com.square.repos.domain.usecase.GetReposUseCase
import com.square.repos.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    val repos = getReposUseCase().cachedIn(viewModelScope)

    val bookmarks = getBookmarksUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun toggleBookmark(repoId: Long) {
        viewModelScope.launch {
            toggleBookmarkUseCase(repoId)
        }
    }
}