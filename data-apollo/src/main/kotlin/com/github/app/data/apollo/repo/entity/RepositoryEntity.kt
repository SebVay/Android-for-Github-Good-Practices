package com.github.app.data.apollo.repo.entity

data class RepoEntity(
    val name: String,
    val description: String?,
    val owner: OwnerEntity,
    val stargazerCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val pullRequestsCount: Int,
    val discussionsCount: Int,
    val url: String,
    val homepageUrl: String?,
    val openGraphImageUrl: String,
    val languages: List<LanguageEntity>,
)

data class OwnerEntity(
    val login: String,
    val avatarUrl: String,
)

data class LanguageEntity(
    val name: String,
    val color: String?,
    val size: Int,
)
