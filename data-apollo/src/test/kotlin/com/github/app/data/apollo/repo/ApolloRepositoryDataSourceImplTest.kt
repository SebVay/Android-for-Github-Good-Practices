package com.github.app.data.apollo.repo

import com.apollographql.apollo.ApolloClient
import com.github.app.data.apollo.RepositoriesQuery
import com.github.app.data.apollo.RepositoriesQuery.Data
import com.github.app.data.apollo.RepositoriesQuery.Node
import com.github.app.data.apollo.RepositoriesQuery.Search
import com.github.app.data.apollo.ext.executeQuery
import com.github.app.data.apollo.repo.entity.RepoEntity
import com.github.app.data.apollo.repo.mapper.RepositoryMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApolloRepositoryDataSourceImplTest {

    private val apolloClient: ApolloClient = mockk()
    private val mapper: RepositoryMapper = mockk()
    private val apolloRepositoryDataSource = ApolloRepositoryDataSourceImpl(apolloClient, mapper)

    @Test
    fun `verify getRepositories returns mapped expected repo from ApolloClient`() = runTest {
        // Given
        val givenQuery = "is:public"
        val givenRepositoryNode = mockk<RepositoriesQuery.OnRepository>()
        val givenResponseData = Data(Search(listOf(Node("Repository", givenRepositoryNode))))

        val expectedRepoEntity = mockk<RepoEntity>()

        coEvery { apolloClient.executeQuery(any<RepositoriesQuery>()) } returns givenResponseData
        coEvery { mapper.invoke(givenRepositoryNode) } returns expectedRepoEntity

        // When
        val result = apolloRepositoryDataSource.getRepositories(givenQuery)

        // Then
        assertEquals(listOf(expectedRepoEntity), result)
    }
}
