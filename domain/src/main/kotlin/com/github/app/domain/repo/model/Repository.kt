package com.github.app.domain.repo.model

data class Repository(
    val name: String,
    val description: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val pullRequestsCount: Int,
    val url: String,
    val homepageUrl: String?,
    val openGraphImageUrl: String,
    val languages: List<Language>,
)

data class Language(
    val name: String,
    val color: String?,
    val percentage: Int,
    val lineOfCode: Int,
)
