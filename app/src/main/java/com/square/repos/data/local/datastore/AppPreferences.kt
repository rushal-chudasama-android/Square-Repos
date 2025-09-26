package com.square.repos.data.local.datastore

data class AppPreferences(
    val isDarkMode: Boolean,
    val language: String,
    val isFirstLaunch: Boolean
)