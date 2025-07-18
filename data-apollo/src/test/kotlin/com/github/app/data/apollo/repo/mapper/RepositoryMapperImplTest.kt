package com.github.app.data.apollo.repo.mapper

import com.github.app.data.apollo.RepositoriesQuery.Discussions
import com.github.app.data.apollo.RepositoriesQuery.Edge
import com.github.app.data.apollo.RepositoriesQuery.Issues
import com.github.app.data.apollo.RepositoriesQuery.Languages
import com.github.app.data.apollo.RepositoriesQuery.Node1
import com.github.app.data.apollo.RepositoriesQuery.OnRepository
import com.github.app.data.apollo.RepositoriesQuery.Owner
import com.github.app.data.apollo.RepositoriesQuery.PullRequests
import com.github.app.data.apollo.repo.entity.LanguageEntity
import com.github.app.data.apollo.repo.entity.OwnerEntity
import com.github.app.data.apollo.repo.entity.RepoEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RepositoryMapperImplTest {

    private val mapper = RepositoryMapperImpl()

    @Test
    fun `verify Repository is well mapped to RepoEntity`() {
        // Given
        val givenId = "id"
        val givenName = "name"
        val givenDescription = "description"
        val givenLogin = "login"
        val givenAvatarUrl = "avatar"
        val givenUrl = "url"
        val givenHomepageUrl = "homepage"
        val givenOpenGraphImageUrl = "image"
        val givenStargazerCount = 1
        val givenForkCount = 2
        val givenIssuesCount = 3
        val givenPullRequestsCount = 4
        val givenDiscussionsCount = 5

        val givenOnRepository = OnRepository(
            id = givenId,
            name = givenName,
            description = givenDescription,
            owner = Owner(givenAvatarUrl, givenLogin),
            stargazerCount = givenStargazerCount,
            forkCount = givenForkCount,
            issues = Issues(givenIssuesCount),
            pullRequests = PullRequests(givenPullRequestsCount),
            discussions = Discussions(givenDiscussionsCount),
            url = givenUrl,
            homepageUrl = givenHomepageUrl,
            openGraphImageUrl = givenOpenGraphImageUrl,
            languages = null,
        )

        // When
        val repoEntity = mapper(givenOnRepository)

        // Then
        val expectedRepoEntity = RepoEntity(
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
            languages = emptyList(),
        )
        assertEquals(expectedRepoEntity, repoEntity)
    }

    @Test
    fun `verify Repository language is well mapped`() {
        // Given
        val givenLanguage1 = "Kotlin"
        val givenColor1 = "#A97BFF"
        val givenLanguageSize1 = 1
        val givenLanguage2 = "Java"
        val givenColor2 = "#DEDEDE"
        val givenLanguageSize2 = 2

        val givenOnRepository = OnRepository(
            id = "id",
            name = "name",
            description = "description",
            owner = Owner("login", "avatar"),
            stargazerCount = 1,
            forkCount = 2,
            issues = Issues(3),
            pullRequests = PullRequests(4),
            discussions = Discussions(5),
            url = "url",
            homepageUrl = "homepage",
            openGraphImageUrl = "image",
            languages = Languages(
                listOf(
                    Edge(givenLanguageSize1, Node1(givenLanguage1, "id", givenColor1)),
                    Edge(givenLanguageSize2, Node1(givenLanguage2, "id", givenColor2)),
                ),
            ),
        )

        // When
        val repoEntity = mapper(givenOnRepository)

        val expectedLanguage = listOf(
            LanguageEntity(givenLanguage1, givenColor1, givenLanguageSize1),
            LanguageEntity(givenLanguage2, givenColor2, givenLanguageSize2),
        )

        // Then
        assertEquals(expectedLanguage, repoEntity.languages)
    }
}
