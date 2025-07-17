package com.github.app.data.mapper

import com.github.app.data.RepositoriesDataSource
import com.github.app.domain.contract.GetTrendingRepositories
import com.github.app.domain.contract.Language
import com.github.app.domain.contract.Repository

internal class GetTrendingRepositoriesImpl(
    private val dataSource: RepositoriesDataSource,
) : GetTrendingRepositories {
    override suspend fun invoke(query: String): List<Repository> = dataSource.getRepositories(query).map { repo ->
        Repository(
            id = repo.id,
            name = repo.name,
            description = repo.description,
            ownerLogin = repo.owner.login,
            ownerAvatarUrl = repo.owner.avatarUrl,
            stargazerCount = repo.stargazerCount,
            forkCount = repo.forkCount,
            issuesCount = repo.issuesCount,
            pullRequestsCount = repo.pullRequestsCount,
            discussionsCount = repo.discussionsCount,
            url = repo.url,
            homepageUrl = repo.homepageUrl,
            openGraphImageUrl = repo.openGraphImageUrl,
            languages = repo.languages.map { language ->
                Language(
                    name = language.name,
                    color = language.color,
                    size = language.size,
                )
            },
        )
    }
}
