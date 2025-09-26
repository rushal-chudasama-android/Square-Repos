package com.square.repos.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.square.repos.data.local.datastore.AppPreferences
import com.square.repos.presentation.bookmarks.BookmarksScreen
import com.square.repos.presentation.details.RepoDetailsScreen
import com.square.repos.presentation.repos.ReposScreen
import com.square.repos.presentation.settings.SettingsScreen

@Composable
fun MainNavGraph(
    navController: NavController,
    preferencesState: AppPreferences,
    onPreferencesUpdate: (AppPreferences) -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "repos"
    ) {
        composable("repos") {
            ReposScreen(
                onRepoClick = { repoId ->
                    navController.navigate("repo/$repoId")
                }
            )
        }
        composable("bookmarks") {
            BookmarksScreen(
                onRepoClick = { repoId ->
                    navController.navigate("repo/$repoId") {
                        launchSingleTop = true
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("settings") {
            SettingsScreen(
                preferencesState = preferencesState,
                onPreferencesUpdate = onPreferencesUpdate
            )
        }
        composable(
            route = "repo/{repoId}",
            arguments = listOf(navArgument("repoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val repoId = backStackEntry.arguments?.getLong("repoId") ?: 0L
            RepoDetailsScreen(repoId = repoId)
        }
    }
}