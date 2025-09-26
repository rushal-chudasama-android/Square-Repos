package com.square.repos.presentation.repos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.square.repos.R
import com.square.repos.ui.components.common.ErrorState
import com.square.repos.ui.components.common.LoadingAnimation
import com.square.repos.ui.components.common.RepoItem

@OptIn(ExperimentalPagingApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReposScreen(
    viewModel: ReposViewModel = hiltViewModel(),
    onRepoClick: (Long) -> Unit
) {
    val repos = viewModel.repos.collectAsLazyPagingItems()
    val bookmarks by viewModel.bookmarks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.square_repositories)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                repos.loadState.refresh is LoadState.Loading -> {
                    LoadingAnimation()
                }

                repos.loadState.refresh is LoadState.Error -> {
                    val error = (repos.loadState.refresh as LoadState.Error).error
                    ErrorState(
                        message = "Failed to load repositories: ${error.message}",
                        onRetry = { repos.retry() }
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            count = repos.itemCount,
                            key = { index -> repos[index]?.id ?: index }
                        ) { index ->
                            repos[index]?.let { repo ->
                                RepoItem(
                                    repo = repo,
                                    isBookmarked = bookmarks.any { it.id == repo.id },
                                    onBookmarkClick = { viewModel.toggleBookmark(repo.id) },
                                    onClick = { onRepoClick(repo.id) },
//                                    modifier = Modifier.animateItemPlacement()
                                )

                                if (index < repos.itemCount - 1) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        item {
                            if (repos.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}