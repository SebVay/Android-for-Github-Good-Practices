package com.github.app.ui.repo.component

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
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import com.github.app.core.ui.component.MultiDevicePreviews
import com.github.app.core.ui.component.card.AppCard
import com.github.app.core.ui.component.text.BABodyLarge
import com.github.app.core.ui.component.text.BATitleLarge
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.screen.RepositoryViewState

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
        onClick = dropUnlessResumed { onClickRepository() },
    ) {
        Column(
            modifier = Modifier
                .padding(GithubAppDimens.Padding.UNIT),
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(vertical = GithubAppDimens.Padding.UNIT)
                        .padding(end = GithubAppDimens.Padding.DOUBLE),
                ) {
                    BATitleLarge(
                        text = repository.repositoryName,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                    )

                    BABodyLarge(
                        text = repository.description,
                        color = LocalColors.current.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                    )
                }

                Spacer(Modifier.size(GithubAppDimens.Padding.UNIT))

                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(GithubAppDimens.Icon.MediumLarge)
                        .clip(RoundedCornerShape(GithubAppDimens.CornerRadius.Icon)),
                    contentDescription = repository.repositoryName,
                    model = repository.imageUrl,
                )
            }
        }
    }
}

@MultiDevicePreviews
@Composable
private fun PreviewRepositoryCard() {
    RepositoryCard(
        onClickRepository = {},
        repository = RepositoryViewState(
            repositoryName = "Sample Repository",
            description = "This is a sample repository description.",
            repositoryId = "",
            imageUrl = "",
            stargazerCount = 12,
            forkCount = 5,
            issuesCount = 80,
            pullRequestsCount = 15,
            authorName = "author",
        ),
    )
}
