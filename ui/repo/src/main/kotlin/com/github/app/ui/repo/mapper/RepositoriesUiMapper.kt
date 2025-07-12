package com.github.app.ui.repo.mapper

import com.github.app.domain.repo.model.Repository
import com.github.app.ui.repo.screen.RepositoryViewState
import kotlinx.collections.immutable.toImmutableList

/**
 * Maps a list of [Repository] domain models to a list of [RepositoryViewState] UI models.
 */
interface RepositoriesUiMapper : (List<Repository>) -> List<RepositoryViewState>

internal class RepositoriesUiMapperImpl : RepositoriesUiMapper {
    override fun invoke(repositories: List<Repository>): List<RepositoryViewState> = repositories.map { repository ->
        RepositoryViewState(
            repositoryId = "",
            imageUrl = repository.ownerAvatarUrl,
            repositoryName = repository.name,
            description = repository.description,
            stargazerCount = repository.stargazerCount,
            forkCount = repository.forkCount,
            issuesCount = repository.issuesCount,
            pullRequestsCount = repository.pullRequestsCount,
            authorName = repository.ownerName,
        )
    }.toImmutableList()
}
