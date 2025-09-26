package com.square.repos

import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.square.repos.core.network.ConnectivityObserver
import com.square.repos.core.network.NetworkMonitor
import com.square.repos.data.local.datastore.AppPreferences
import com.square.repos.data.local.datastore.PreferencesManager
import com.square.repos.presentation.main.MainScreen
import com.square.repos.presentation.splash.SplashScreen
import com.square.repos.ui.components.common.NetworkStatusBar
import com.square.repos.ui.theme.SquareReposTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesManager: PreferencesManager

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val preferencesState by preferencesManager.appPreferences.collectAsState(
                initial = AppPreferences(
                    isDarkMode = false,
                    language = "en",
                    isFirstLaunch = true
                )
            )

            val networkStatus by networkMonitor.observe().collectAsState(
                initial = ConnectivityObserver.Status.Available
            )

            LaunchedEffect(preferencesState.language) {
                updateAppLanguage(preferencesState.language)
            }

            TransparentStatusBarWithDarkIcons()

            SquareReposTheme(
                darkTheme = preferencesState.isDarkMode
            ) {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    val bottomPadding = PaddingValues(
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    // Apply RTL/LTR based on language
                    CompositionLocalProvider(
                        LocalLayoutDirection provides when (preferencesState.language) {
                            "ar" -> LayoutDirection.Rtl
                            else -> LayoutDirection.Ltr
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (preferencesState.isFirstLaunch) {
                                SplashScreen {
                                    preferencesManager.setFirstLaunchCompleted()
                                }
                            } else {
                                MainScreen(
                                    preferencesState = preferencesState,
                                    onPreferencesUpdate = { newPrefs -> }
                                )
                            }

                            NetworkStatusBar(
                                networkStatus = networkStatus,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateAppLanguage(languageCode: String) {
       /* var languageCode : String = languageCode1
        if (languageCode1 == "ar"){
            languageCode= "en"
        }else{
            languageCode= "ar"
        }*/
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        if (TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL) {
            config.setLayoutDirection(locale)
        }

        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }
}

@Composable
private fun TransparentStatusBarWithDarkIcons() {
    val systemUiController = rememberSystemUiController()
    val navBG = colorResource(R.color.white)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
        systemUiController.setNavigationBarColor(
            color = navBG, darkIcons = true
        )
    }
}