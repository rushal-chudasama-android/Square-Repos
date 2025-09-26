package com.square.repos.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.square.repos.R
import com.square.repos.core.network.ConnectivityObserver

@Composable
fun NetworkStatusBar(
    networkStatus: ConnectivityObserver.Status,
    modifier: Modifier = Modifier
) {
    val isVisible = networkStatus != ConnectivityObserver.Status.Available

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = when (networkStatus) {
                ConnectivityObserver.Status.Lost -> MaterialTheme.colorScheme.errorContainer
                ConnectivityObserver.Status.Losing -> MaterialTheme.colorScheme.errorContainer
                ConnectivityObserver.Status.Unavailable -> MaterialTheme.colorScheme.errorContainer
                else -> MaterialTheme.colorScheme.surface
            },
            contentColor = when (networkStatus) {
                ConnectivityObserver.Status.Lost -> MaterialTheme.colorScheme.onErrorContainer
                ConnectivityObserver.Status.Losing -> MaterialTheme.colorScheme.onErrorContainer
                ConnectivityObserver.Status.Unavailable -> MaterialTheme.colorScheme.onErrorContainer
                else -> MaterialTheme.colorScheme.onSurface
            },
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WifiOff,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = stringResource(R.string.no_internet),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.check_connection),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                if (networkStatus == ConnectivityObserver.Status.Unavailable) {
                    TextButton(onClick = { /* Handle retry */ }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}