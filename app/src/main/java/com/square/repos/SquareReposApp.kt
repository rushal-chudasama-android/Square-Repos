package com.square.repos

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import com.square.repos.data.local.datastore.PreferencesManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class SquareReposApp : Application() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate() {
        super.onCreate()
        AppContext.initialize(this)
    }

    override fun attachBaseContext(base: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(base)
        val language = prefs.getString("language", "en") ?: "en"

        super.attachBaseContext(updateBaseContextLocale(base, language))
    }

    private fun updateBaseContextLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }
}

object AppContext {
    private lateinit var instance: Context

    fun initialize(context: Context) {
        instance = context
    }

    fun get(): Context = instance
}