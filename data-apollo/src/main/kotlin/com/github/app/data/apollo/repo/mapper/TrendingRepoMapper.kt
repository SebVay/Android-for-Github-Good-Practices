package com.github.app.data.apollo.repo.mapper

import com.github.app.data.apollo.TrendingRepositoriesQuery.OnRepository
import com.github.app.data.apollo.repo.entity.LanguageEntity
import com.github.app.data.apollo.repo.entity.OwnerEntity
import com.github.app.data.apollo.repo.entity.RepoEntity

interface TrendingRepoMapper : (OnRepository) -> RepoEntity

internal class TrendingRepoMapperImpl : TrendingRepoMapper {
    override fun invoke(repository: OnRepository): RepoEntity = with(repository) {
        RepoEntity(
            name = name,
            description = description,
            owner = OwnerEntity(
                login = owner.login,
                avatarUrl = owner.avatarUrl.toString(),
            ),
            stargazerCount = stargazerCount,
            forkCount = forkCount,
            issuesCount = issues.totalCount,
            pullRequestsCount = pullRequests.totalCount,
            url = url.toString(),
            homepageUrl = homepageUrl.toString(),
            openGraphImageUrl = openGraphImageUrl.toString(),
            languages = languages?.edges.orEmpty().filterNotNull().map { language ->
                LanguageEntity(
                    name = language.node.name,
                    color = language.node.color,
                    size = language.size,
                )
            },
        )
    }
}
