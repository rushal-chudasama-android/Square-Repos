package com.square.repos.ui.localization

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object Localization {
    fun setAppLanguage(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    fun isRtlLanguage(languageCode: String): Boolean {
        return languageCode == "ar"
    }
}