package com.github.app.core.ui.component.card

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.LocalColors

@Composable
fun AppCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = GithubAppDimens.Elevation.CardDefault,
            pressedElevation = GithubAppDimens.Elevation.CardPressed,
        ),
        colors = CardDefaults.cardColors(
            containerColor = LocalColors.current.surface,
        ),
        shape = RoundedCornerShape(GithubAppDimens.CornerRadius.Card),
        onClick = onClick,
        content = content,
    )
}
