package com.github.app.data

import com.github.app.data.apollo.repo.TrendingRepoDataSource
import com.github.app.data.apollo.repo.entity.RepoEntity

interface RepoDataSource {
    suspend fun getTrendingRepo(): List<RepoEntity>
}

internal class RepoDataSourceImpl(
    private val trendingRepoDataSource: TrendingRepoDataSource,
) : RepoDataSource {
    override suspend fun getTrendingRepo(): List<RepoEntity> = trendingRepoDataSource.getTrendingRepo()
}
