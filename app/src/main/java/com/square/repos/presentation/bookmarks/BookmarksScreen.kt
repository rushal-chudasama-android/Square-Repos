package com.square.repos.presentation.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.square.repos.R
import com.square.repos.ui.components.common.RepoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    onRepoClick: (Long) -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: BookmarksViewModel = hiltViewModel()
) {

    val bookmarks by viewModel.bookmarks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.bookmarks),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    // Add back button if needed for navigation
                    // IconButton(onClick = onBackClick) {
                    //     Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    // }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                bookmarks.isEmpty() -> {
                    // Empty state
                    EmptyBookmarksState(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    // Bookmarks list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            items = bookmarks,
                            key = { it.id }
                        ) { repo ->
                            RepoItem(
                                repo = repo,
                                isBookmarked = true,
                                onBookmarkClick = {
                                    viewModel.toggleBookmark(repo.id)
                                },
                                onClick = {
                                    onRepoClick(repo.id)
                                },
//                                modifier = Modifier.animateItemPlacement()
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }

    // Handle initial load or refresh
    LaunchedEffect(Unit) {
        viewModel.loadBookmarks()
    }
}

@Composable
fun EmptyBookmarksState(
    modifier: Modifier = Modifier
) {
    EmptyState(
        modifier = modifier,
        icon = Icons.Outlined.BookmarkBorder,
        title = stringResource(R.string.no_bookmarks),
        subtitle = "Your bookmarked repositories will appear here",
        iconSize = 120.dp
    )
}

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    iconSize: Dp = 80.dp,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = iconTint
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            subtitle?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
