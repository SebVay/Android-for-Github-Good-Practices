package com.github.app.ui.repo.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.github.app.core.ui.component.text.AppLabelLarge
import com.github.app.core.ui.theme.GithubAppDimens.Padding
import com.github.app.ui.repo.compose.attr.RepositoryDimens
import com.github.app.ui.repo.compose.toComposeColor
import com.github.app.ui.repo.screen.state.LanguageViewState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun LanguageRow(
    states: ImmutableList<LanguageViewState>,
) {
    FlowRow(
        modifier = Modifier.padding(
            start = Padding.DOUBLE,
            top = Padding.DOUBLE
        ),
        horizontalArrangement = Arrangement.spacedBy(Padding.DOUBLE),
        verticalArrangement = Arrangement.spacedBy(Padding.UNIT)
    ) {
        states.forEach { language ->

            val languageColor = language.color.toComposeColor()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Padding.UNIT)
            ) {
                Box(
                    modifier = Modifier
                        .size(RepositoryDimens.LanguageCircleSize)
                        .clip(CircleShape)
                        .background(languageColor),
                )

                AppLabelLarge(
                    modifier = Modifier,
                    text = language.name
                )
            }
        }
    }
}
