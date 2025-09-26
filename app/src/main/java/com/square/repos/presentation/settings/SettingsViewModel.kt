package com.square.repos.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.square.repos.data.local.datastore.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun updateDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferencesManager.updateDarkMode(isDarkMode)
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            preferencesManager.updateLanguage(language)
        }
    }
}