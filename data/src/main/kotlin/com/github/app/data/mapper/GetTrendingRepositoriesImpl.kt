package com.github.app.data.mapper

import com.github.app.data.RepoDataSource
import com.github.app.domain.contract.GetTrendingRepositories
import com.github.app.domain.contract.Language
import com.github.app.domain.contract.Repository

internal class GetTrendingRepositoriesImpl(
    private val dataSource: RepoDataSource,
) : GetTrendingRepositories {
    override suspend fun invoke(): List<Repository> = dataSource.getTrendingRepo().map { repo ->
        Repository(
            name = repo.name,
            description = repo.description,
            ownerLogin = repo.owner.login,
            ownerAvatarUrl = repo.owner.avatarUrl,
            stargazerCount = repo.stargazerCount,
            forkCount = repo.forkCount,
            issuesCount = repo.issuesCount,
            pullRequestsCount = repo.pullRequestsCount,
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
