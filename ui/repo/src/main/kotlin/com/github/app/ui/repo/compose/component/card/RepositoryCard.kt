package com.github.app.ui.repo.compose.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.github.app.core.ui.component.MultiDevicePreviews
import com.github.app.core.ui.component.card.AppCard
import com.github.app.core.ui.component.text.AppBodyLarge
import com.github.app.core.ui.component.text.AppTitleLarge
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.GithubAppDimens.Padding
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.compose.component.LanguageLine
import com.github.app.ui.repo.compose.component.LanguageRow
import com.github.app.ui.repo.compose.component.StatRow
import com.github.app.ui.repo.screen.state.LanguageColorViewState.CustomColor
import com.github.app.ui.repo.screen.state.LanguageColorViewState.DefaultColor
import com.github.app.ui.repo.screen.state.LanguageLineViewState
import com.github.app.ui.repo.screen.state.LanguageProgressionViewState
import com.github.app.ui.repo.screen.state.LanguageViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState
import kotlinx.collections.immutable.persistentListOf

/**
 * A Composable function that displays a card for a repository.
 *
 * This card includes the repository's icon, name, description, stats (stars, issues, forks, etc.),
 * and programming languages.
 *
 * @param onClickRepository A lambda function that will be invoked when the card is clicked.
 * @param repository The [RepositoryViewState] containing the data to display for the repository.
 * @param modifier An optional [Modifier] to be applied to the card.
 */
@Composable
internal fun RepositoryCard(
    onClickRepository: () -> Unit,
    repository: RepositoryViewState,
    modifier: Modifier = Modifier,
) {
    AppCard(
        modifier = modifier,
        onClick = onClickRepository,
    ) {
        Column(
            modifier = Modifier
                .padding(Padding.UNIT),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val fullName = remember { repository.authorName + "/" + repository.name }

                AppTitleLarge(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = Padding.UNIT)
                        .padding(end = Padding.DOUBLE),
                    text = fullName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    textsToBold = persistentListOf(repository.name),
                )

                Spacer(Modifier.size(Padding.UNIT))

                AsyncImage(
                    modifier = Modifier
                        .size(GithubAppDimens.Icon.MediumLarge)
                        .clip(remember { RoundedCornerShape(GithubAppDimens.CornerRadius.Icon) }),
                    contentDescription = repository.name,
                    model = repository.imageUrl,
                )
            }

            if (repository.description != null) {
                AppBodyLarge(
                    modifier = Modifier.padding(top = Padding.UNIT),
                    text = repository.description,
                    color = LocalColors.current.onSurfaceSecondary,
                )
            }

            StatRow(repository)

            if (repository.languages.isNotEmpty()) {
                LanguageRow(
                    repository.languages,
                    Modifier.padding(
                        start = Padding.UNIT + Padding.HALF,
                        top = Padding.DOUBLE,
                    ),
                )
            }
        }

        if (repository.languageLine != null) {
            LanguageLine(repository.languageLine)
        }
    }
}

@MultiDevicePreviews
@Composable
private fun PreviewRepositoryCard() {
    RepositoryCard(
        onClickRepository = {},
        repository = RepositoryViewState(
            name = "Sample Repository",
            description = "This is a sample repository description.",
            id = "",
            imageUrl = "",
            stargazerCount = 12,
            forkCount = 5,
            issuesCount = 80,
            pullRequestsCount = 15,
            authorName = "author",
            discussionsCount = 123,
            languages = persistentListOf(
                LanguageViewState("Kotlin", CustomColor("#A97BFF"), 0.4F),
                LanguageViewState("Java", CustomColor("#B07219"), 0.2F),
                LanguageViewState("Shell", DefaultColor, 0.4F),
            ),
            languageLine = LanguageLineViewState(
                languageProgression = persistentListOf(
                    LanguageProgressionViewState(CustomColor("#A97BFF"), 0.0F),
                    LanguageProgressionViewState(CustomColor("#B07219"), 0.9F),
                    LanguageProgressionViewState(DefaultColor, 1.0F),
                ),
            ),
        ),
    )
}
