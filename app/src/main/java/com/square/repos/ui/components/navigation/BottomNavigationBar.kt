package com.square.repos.ui.components.navigation

import android.view.View
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.square.repos.R

@Composable
fun BottomNavigationBar(
    currentDestination: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    isLayoutRtl()

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        NavigationBarItem(
            selected = currentDestination == "repos",
            onClick = { onNavigate("repos") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.repositories)
                )
            },
            label = { Text(stringResource(R.string.repositories)) }
        )

        NavigationBarItem(
            selected = currentDestination == "bookmarks",
            onClick = { onNavigate("bookmarks") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = stringResource(R.string.bookmarks)
                )
            },
            label = { Text(stringResource(R.string.bookmarks)) }
        )

        NavigationBarItem(
            selected = currentDestination == "settings",
            onClick = { onNavigate("settings") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            },
            label = { Text(stringResource(R.string.settings)) }
        )
    }
}

@Composable
fun isLayoutRtl(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
}