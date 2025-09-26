package com.square.repos.core.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun Context.wrapLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}
