package com.github.app.ui.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor
import com.github.app.domain.repo.model.Repository
import com.github.app.ui.repo.screen.state.LanguageColorViewState
import com.github.app.ui.repo.screen.state.LanguageLineViewState
import com.github.app.ui.repo.screen.state.LanguageProgressionViewState
import com.github.app.ui.repo.screen.state.LanguageViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Test

class RepositoryViewStateMapperImplTest {

    private val repositoryViewStateMapper = RepositoryViewStateMapperImpl()

    @Test
    fun `verify a Repository is well mapped to RepositoryViewState`() {
        // Given
        val givenId = "id"
        val givenName = "name"
        val givenDescription = "description"
        val givenLogin = "login"
        val givenAvatarUrl = "avatar"
        val givenStargazerCount = 1
        val givenForkCount = 2
        val givenIssueCount = 3
        val givenPullRequestCount = 4
        val givenDiscussionCount = 5
        val givenUrl = "url"
        val givenHomepageUrl = "homepageUrl"
        val givenOpenGraphImageUrl = "openGraphImageUrl"

        val givenRepo = Repository(
            id = givenId,
            name = givenName,
            description = givenDescription,
            ownerName = givenLogin,
            ownerAvatarUrl = givenAvatarUrl,
            stargazerCount = givenStargazerCount,
            forkCount = givenForkCount,
            issueCount = givenIssueCount,
            pullRequestCount = givenPullRequestCount,
            discussionCount = givenDiscussionCount,
            url = givenUrl,
            homepageUrl = givenHomepageUrl,
            openGraphImageUrl = givenOpenGraphImageUrl,
            languages = emptyList(),
        )

        // When
        val repositoryViewState = repositoryViewStateMapper(givenRepo)

        // Then
        val expected = RepositoryViewState(
            id = givenId,
            name = givenName,
            description = givenDescription,
            authorName = givenLogin,
            imageUrl = givenAvatarUrl,
            stargazerCount = givenStargazerCount,
            forkCount = givenForkCount,
            issuesCount = givenIssueCount,
            pullRequestsCount = givenPullRequestCount,
            discussionsCount = givenDiscussionCount,
            languages = persistentListOf(),
            languageLine = null,
        )

        repositoryViewState shouldBeEqual expected
    }

    @Test
    fun `verify languages are well mapped to LanguageViewState`() {
        // Given
        val givenName1 = "name1"
        val givenColor1 = "#000000"
        val givenWeight1 = 0.5F
        val givenName2 = "name2"
        val givenWeight2 = 0.4F
        val givenRepo = EMPTY_REPOSITORY.copy(
            languages = listOf(
                Language(
                    name = givenName1,
                    color = LanguageColor.CustomColor(givenColor1),
                    weight = givenWeight1,
                    size = 0,
                ),
                Language(
                    name = givenName2,
                    color = LanguageColor.DefaultColor,
                    weight = givenWeight2,
                    size = 0,
                ),
            ),
        )

        // When
        val repositoryViewState = repositoryViewStateMapper(givenRepo)

        // Then
        repositoryViewState.languages shouldBeEqual listOf(
            LanguageViewState(
                name = givenName1,
                color = LanguageColorViewState.CustomColor(givenColor1),
                weight = givenWeight1,
            ),
            LanguageViewState(
                name = givenName2,
                color = LanguageColorViewState.DefaultColor,
                weight = givenWeight2,
            ),
        )
    }

    @Test
    fun `verify languages are well mapped to LanguageLineViewState`() {
        // Given
        val givenName1 = "name1"
        val givenName2 = "name2"
        val givenName3 = "name3"
        val givenColor1 = "#000000"
        val givenColor3 = "#FFFFFF"
        val givenWeight1 = 0.5F
        val givenWeight2 = 0.4F
        val givenWeight3 = 0.1F
        val givenRepo = EMPTY_REPOSITORY.copy(
            languages = listOf(
                Language(
                    name = givenName1,
                    color = LanguageColor.CustomColor(givenColor1),
                    weight = givenWeight1,
                    size = 0,
                ),
                Language(
                    name = givenName2,
                    color = LanguageColor.DefaultColor,
                    weight = givenWeight2,
                    size = 0,
                ),
                Language(
                    name = givenName3,
                    color = LanguageColor.CustomColor(givenColor3),
                    weight = givenWeight3,
                    size = 0,
                ),
            ),
        )

        // When
        val repositoryViewState = repositoryViewStateMapper(givenRepo)

        // Then
        repositoryViewState.languageLine shouldBe LanguageLineViewState(
            persistentListOf(
                LanguageProgressionViewState(
                    color = LanguageColorViewState.CustomColor(hexColor = "#000000"),
                    startWeight = 0.0f,
                ),
                LanguageProgressionViewState(
                    color = LanguageColorViewState.DefaultColor,
                    startWeight = 0.5f,
                ),
                LanguageProgressionViewState(
                    color = LanguageColorViewState.CustomColor(hexColor = "#FFFFFF"),
                    startWeight = 0.9f,
                ),
            ),
        )
    }
}

private val EMPTY_REPOSITORY = Repository(
    id = "",
    name = "",
    description = "",
    ownerName = "",
    ownerAvatarUrl = "",
    stargazerCount = 0,
    forkCount = 0,
    issueCount = 0,
    pullRequestCount = 0,
    discussionCount = 0,
    url = "",
    homepageUrl = "",
    openGraphImageUrl = "",
    languages = emptyList(),
)
