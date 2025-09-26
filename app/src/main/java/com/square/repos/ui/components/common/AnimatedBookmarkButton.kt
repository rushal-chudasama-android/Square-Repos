package com.square.repos.ui.components.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AnimatedBookmarkButton(
    isBookmarked: Boolean,
    onBookmarkClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldAnimate by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isBookmarked, label = "bookmark")

    val color by transition.animateColor(label = "color") { bookmarked ->
        if (bookmarked) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline
    }

    val scale by transition.animateFloat(
        transitionSpec = {
            if (isBookmarked) {
                spring(dampingRatio = 0.6f, stiffness = 800f)
            } else {
                tween(durationMillis = 200)
            }
        },
        label = "scale"
    ) { bookmarked ->
        if (bookmarked && shouldAnimate) 1.3f else 1f
    }

    IconButton(
        onClick = {
            shouldAnimate = true
            onBookmarkClick(!isBookmarked)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
            contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark",
            tint = color,
            modifier = Modifier
                .size(24.dp)
                .scale(scale)
        )
    }

    LaunchedEffect(isBookmarked) {
        if (shouldAnimate) {
            delay(300)
            shouldAnimate = false
        }
    }
}