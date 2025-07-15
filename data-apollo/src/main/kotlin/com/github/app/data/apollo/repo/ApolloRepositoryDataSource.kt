package com.github.app.data.apollo.repo

import com.apollographql.apollo.ApolloClient
import com.github.app.data.apollo.RepositoriesQuery
import com.github.app.data.apollo.ext.executeQuery
import com.github.app.data.apollo.repo.entity.RepoEntity
import com.github.app.data.apollo.repo.mapper.RepositoryMapper

interface ApolloRepositoryDataSource {
    suspend fun getRepositories(query: String): List<RepoEntity>
}

internal class ApolloRepositoryDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val mapper: RepositoryMapper,
) : ApolloRepositoryDataSource {
    override suspend fun getRepositories(query: String): List<RepoEntity> {
        return apolloClient.executeQuery(RepositoriesQuery(query))
            .search
            .nodes.orEmpty()
            .mapNotNull { node -> node?.onRepository }
            .map(mapper)
    }
}
