package com.github.app.ui.repo.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.github.app.core.ui.component.MultiDevicePreviews
import com.github.app.core.ui.component.card.AppCard
import com.github.app.core.ui.component.text.AppBodyLarge
import com.github.app.core.ui.component.text.AppBodyMedium
import com.github.app.core.ui.component.text.AppTitleLarge
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.R
import com.github.app.ui.repo.icon.RepositoryIcons
import com.github.app.ui.repo.icon.pack.Discussion
import com.github.app.ui.repo.icon.pack.Error
import com.github.app.ui.repo.icon.pack.Fork
import com.github.app.ui.repo.icon.pack.PullRequest
import com.github.app.ui.repo.icon.pack.Star
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
                val fullName = remember { repository.authorName + "/" + repository.name }

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
                        .clip(remember { RoundedCornerShape(GithubAppDimens.CornerRadius.Icon) }),
                    contentDescription = repository.name,
                    model = repository.imageUrl,
                )
            }

            AppBodyLarge(
                modifier = Modifier.padding(top = GithubAppDimens.Padding.UNIT),
                text = repository.description,
                color = LocalColors.current.onSurfaceSecondary,
            )

            RepositoryStats(repository)
        }
    }
}

@Composable
private fun RepositoryStats(repository: RepositoryViewState) {
    // FlowRow to maxLines=1 is a little niceties that will "hide" the last items if they don't fit
    FlowRow(
        modifier = Modifier.padding(top = GithubAppDimens.Padding.QUAD),
        horizontalArrangement = Arrangement.spacedBy(GithubAppDimens.Padding.UNIT),
        maxLines = 1,
    ) {
        Stat(
            stat = repository.stargazerCount.toString(),
            statLabel = stringResource(R.string.repository_stars),
            painter = rememberVectorPainter(RepositoryIcons.Star),
        )

        if (repository.issuesCount > 0) {
            Stat(
                stat = repository.issuesCount.toString(),
                statLabel = stringResource(R.string.repository_issues),
                painter = rememberVectorPainter(RepositoryIcons.Error),
            )
        }

        if (repository.forkCount > 0) {
            Stat(
                stat = repository.forkCount.toString(),
                statLabel = stringResource(R.string.repository_forks),
                painter = rememberVectorPainter(RepositoryIcons.Fork),
            )
        }

        if (repository.pullRequestsCount > 0) {
            Stat(
                stat = repository.pullRequestsCount.toString(),
                statLabel = stringResource(R.string.repository_pull_requests),
                painter = rememberVectorPainter(RepositoryIcons.PullRequest),
            )
        }

        if (repository.discussionsCount > 0) {
            Stat(
                stat = repository.discussionsCount.toString(),
                statLabel = stringResource(R.string.repository_discussions),
                painter = rememberVectorPainter(RepositoryIcons.Discussion),
            )
        }
    }
}

@Composable
private fun Stat(
    stat: String,
    statLabel: String,
    painter: VectorPainter,
) {
    Column {
        Row {
            Icon(
                modifier = Modifier.size(GithubAppDimens.Icon.SmallMedium),
                painter = painter,
                tint = LocalColors.current.onSurfaceSecondary,
                contentDescription = null,
            )

            AppBodyLarge(
                modifier = Modifier
                    .padding(start = GithubAppDimens.Padding.UNIT)
                    .align(Alignment.CenterVertically),
                text = stat,
            )
        }

        Row {
            Spacer(
                Modifier
                    .padding(start = GithubAppDimens.Padding.UNIT)
                    .size(GithubAppDimens.Icon.SmallMedium),
            )

            AppBodyMedium(
                text = statLabel,
                color = LocalColors.current.onSurfaceSecondary,
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
            discussionsCount = 123,
        ),
    )
}
