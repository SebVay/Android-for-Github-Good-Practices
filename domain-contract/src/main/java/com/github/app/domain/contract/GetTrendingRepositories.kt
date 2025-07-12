package com.github.app.domain.contract

fun interface GetTrendingRepositories : suspend () -> List<Repository>

data class Repository(
    val name: String,
    val description: String?,
    val ownerLogin: String,
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
    val size: Int,
)
