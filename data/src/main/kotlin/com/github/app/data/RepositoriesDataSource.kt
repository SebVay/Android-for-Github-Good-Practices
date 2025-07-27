package com.github.app.data

import com.github.app.data.apollo.repo.ApolloRepositoryDataSource
import com.github.app.data.apollo.repo.entity.RepoEntity

interface RepositoriesDataSource {
    suspend fun getRepositories(query: String): List<RepoEntity>
}

internal class RepositoriesDataSourceImpl(
    private val apolloRepositoryDataSource: ApolloRepositoryDataSource,
) : RepositoriesDataSource {
    override suspend fun getRepositories(
        query: String,
    ): List<RepoEntity> {
        return apolloRepositoryDataSource.getRepositories(query)
    }
}
