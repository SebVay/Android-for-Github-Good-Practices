package com.github.app.ui.repo.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.screen.RepositoryViewState
import kotlinx.collections.immutable.persistentListOf

/**
 * A Composable function that displays a card for a repository.
 *
 * This card includes the repository's icon, name, and baseline. The card is clickable and will
 * invoke the [onClickRepository] lambda when pressed.
 *
 * @param onClickRepository A lambda function that will be invoked when the card is clicked.
 *                          It receives the [RepositoryViewState] associated with the clicked card.
 * @param repository The [RepositoryViewState] containing the data to display for the repository.
 * @param modifier An optional [Modifier] to be applied to the card.
 */
@Composable
fun RepositoryCard(
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
                .padding(GithubAppDimens.Padding.UNIT),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val fullName = repository.authorName + "/" + repository.name

                AppTitleLarge(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = GithubAppDimens.Padding.UNIT)
                        .padding(end = GithubAppDimens.Padding.DOUBLE),
                    text = fullName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    textsToBold = persistentListOf(repository.name),
                )

                Spacer(Modifier.size(GithubAppDimens.Padding.UNIT))

                AsyncImage(
                    modifier = Modifier
                        .size(GithubAppDimens.Icon.MediumLarge)
                        .clip(RoundedCornerShape(GithubAppDimens.CornerRadius.Icon)),
                    contentDescription = repository.name,
                    model = repository.imageUrl,
                )
            }
            AppBodyLarge(
                modifier = Modifier.padding(top = GithubAppDimens.Padding.UNIT),
                text = repository.description,
                color = LocalColors.current.onSurfaceVariant,
            )
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
        ),
    )
}
