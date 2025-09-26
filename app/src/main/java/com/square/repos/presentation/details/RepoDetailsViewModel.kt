package com.square.repos.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.square.repos.domain.usecase.GetRepoUseCase
import com.square.repos.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val getRepoUseCase: GetRepoUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _repoId = MutableStateFlow<Long?>(null)

    val repo = _repoId.flatMapLatest { repoId ->
        repoId?.let { getRepoUseCase(it) } ?: flowOf(null)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun setRepoId(repoId: Long) {
        _repoId.value = repoId
    }

    fun toggleBookmark() {
        _repoId.value?.let { repoId ->
            viewModelScope.launch {
                toggleBookmarkUseCase(repoId)
            }
        }
    }
}