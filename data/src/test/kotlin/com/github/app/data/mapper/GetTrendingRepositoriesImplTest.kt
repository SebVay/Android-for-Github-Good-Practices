package com.github.app.data.mapper

import com.github.app.data.RepositoriesDataSource
import com.github.app.data.apollo.repo.entity.LanguageEntity
import com.github.app.data.apollo.repo.entity.OwnerEntity
import com.github.app.data.apollo.repo.entity.RepoEntity
import com.github.app.domain.contract.Language
import com.github.app.domain.contract.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetTrendingRepositoriesImplTest {

    private val dataSource: RepositoriesDataSource = mockk()
    private val getTrendingRepositories = GetTrendingRepositoriesImpl(dataSource)

    @Test
    fun `verify the entities from the datasource are mapped to repositories`() = runTest {
        // Given
        val givenQuery = "is:public"
        val givenId = "id"
        val givenName = "name"
        val givenDescription = "description"
        val givenLogin = "login"
        val givenAvatarUrl = "avatar"
        val givenUrl = "url"
        val givenHomepageUrl = "homepage"
        val givenOpenGraphImageUrl = "image"
        val givenLanguageName = "Kotlin"
        val givenLanguageColor = "#A97BFF"
        val givenStargazerCount = 1
        val givenForkCount = 2
        val givenIssuesCount = 3
        val givenPullRequestsCount = 4
        val givenDiscussionsCount = 5
        val repoFromDataSource = RepoEntity(
            id = givenId,
            name = givenName,
            description = givenDescription,
            owner = OwnerEntity(
                login = givenLogin,
                avatarUrl = givenAvatarUrl,
            ),
            stargazerCount = givenStargazerCount,
            forkCount = givenForkCount,
            issuesCount = givenIssuesCount,
            pullRequestsCount = givenPullRequestsCount,
            discussionsCount = givenDiscussionsCount,
            url = givenUrl,
            homepageUrl = givenHomepageUrl,
            openGraphImageUrl = givenOpenGraphImageUrl,
            languages = listOf(LanguageEntity(name = givenLanguageName, color = givenLanguageColor, size = 1)),
        )
        coEvery { dataSource.getRepositories(givenQuery) } returns listOf(repoFromDataSource)

        // When
        val repositories = getTrendingRepositories(givenQuery)

        // Then
        val expectedRepositories = listOf(
            Repository(
                id = givenId,
                name = givenName,
                description = givenDescription,
                ownerLogin = givenLogin,
                ownerAvatarUrl = givenAvatarUrl,
                stargazerCount = givenStargazerCount,
                forkCount = givenForkCount,
                issuesCount = givenIssuesCount,
                pullRequestsCount = givenPullRequestsCount,
                discussionsCount = givenDiscussionsCount,
                url = givenUrl,
                homepageUrl = givenHomepageUrl,
                openGraphImageUrl = givenOpenGraphImageUrl,
                languages = listOf(Language(name = givenLanguageName, color = givenLanguageColor, size = 1)),
            ),
        )

        assertEquals(expectedRepositories, repositories)
    }
}
