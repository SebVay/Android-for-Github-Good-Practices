package com.github.app.data.apollo.repo

import com.apollographql.apollo.ApolloClient
import com.github.app.data.apollo.BaseApolloDataSource
import com.github.app.data.apollo.TrendingRepositoriesQuery
import com.github.app.data.apollo.repo.entity.RepoEntity
import com.github.app.data.apollo.repo.mapper.TrendingRepoMapper

interface TrendingRepoDataSource {
    suspend fun getTrendingRepo(): List<RepoEntity>
}

internal class TrendingRepoDataSourceImpl(
    apolloClient: ApolloClient,
    private val mapper: TrendingRepoMapper,
) : BaseApolloDataSource(apolloClient),
    TrendingRepoDataSource {
    override suspend fun getTrendingRepo(): List<RepoEntity> = executeQuery(TrendingRepositoriesQuery())
        .search
        .nodes.orEmpty()
        .mapNotNull { node -> node?.onRepository }
        .map { repository -> mapper(repository) }
}
