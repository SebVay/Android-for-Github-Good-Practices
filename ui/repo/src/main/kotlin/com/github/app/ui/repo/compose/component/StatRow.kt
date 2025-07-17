package com.github.app.ui.repo.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.github.app.core.ui.component.text.AppBodyLarge
import com.github.app.core.ui.component.text.AppBodyMedium
import com.github.app.core.ui.theme.GithubAppDimens
import com.github.app.core.ui.theme.GithubAppDimens.Padding
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.R
import com.github.app.ui.repo.compose.icon.RepositoryIcons
import com.github.app.ui.repo.compose.icon.pack.Discussion
import com.github.app.ui.repo.compose.icon.pack.Error
import com.github.app.ui.repo.compose.icon.pack.Fork
import com.github.app.ui.repo.compose.icon.pack.PullRequest
import com.github.app.ui.repo.compose.icon.pack.Star
import com.github.app.ui.repo.screen.state.RepositoryViewState

@Composable
internal fun StatRow(repository: RepositoryViewState) {
    // FlowRow to maxLines=1 is a little niceties that will "hide" the last items if they don't fit
    FlowRow(
        modifier = Modifier.padding(top = Padding.QUAD),
        horizontalArrangement = Arrangement.spacedBy(Padding.UNIT),
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
                    .padding(start = Padding.UNIT)
                    .align(Alignment.CenterVertically),
                text = stat,
            )
        }

        Row {
            Spacer(
                Modifier
                    .padding(start = Padding.UNIT)
                    .size(GithubAppDimens.Icon.SmallMedium),
            )

            AppBodyMedium(
                text = statLabel,
                color = LocalColors.current.onSurfaceSecondary,
            )
        }
    }
}
