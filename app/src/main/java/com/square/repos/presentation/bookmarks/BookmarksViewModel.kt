package com.square.repos.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.square.repos.data.local.database.entity.RepoEntity
import com.square.repos.domain.usecase.GetBookmarksUseCase
import com.square.repos.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _bookmarks = getBookmarksUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val bookmarks: StateFlow<List<RepoEntity>> = _bookmarks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun toggleBookmark(repoId: Long) {
        viewModelScope.launch {
            try {
                toggleBookmarkUseCase(repoId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadBookmarks() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlinx.coroutines.delay(300)
            _isLoading.value = false
        }
    }
}