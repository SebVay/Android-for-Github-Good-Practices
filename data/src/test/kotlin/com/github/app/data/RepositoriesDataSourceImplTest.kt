package com.github.app.data

import com.github.app.data.apollo.repo.ApolloRepositoryDataSource
import com.github.app.data.apollo.repo.entity.RepoEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepositoriesDataSourceImplTest {

    private val apolloRepositoryDataSource: ApolloRepositoryDataSource = mockk()
    private val repositoriesDataSource = RepositoriesDataSourceImpl(apolloRepositoryDataSource)

    @Test
    fun `verify getRepositories calls apolloRepositoryDataSource and return its result`() = runTest {
        // Given
        val givenQuery = "is:public"
        val expectedRepoEntities = mockk<List<RepoEntity>>()
        coEvery { apolloRepositoryDataSource.getRepositories(givenQuery) } returns expectedRepoEntities

        // When
        val result = repositoriesDataSource.getRepositories(givenQuery)

        // Then
        assertEquals(expectedRepoEntities, result)
    }
}
