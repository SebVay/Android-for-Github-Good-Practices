package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor.DefaultColor
import com.github.app.domain.repo.model.Repository
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import com.github.app.domain.contract.Language as ContractLanguage
import com.github.app.domain.contract.Repository as ContractRepository

class TrendingRepositoryMapperImplTest {

    private val languageMapper = mockk<LanguageMapper>()

    private val mapper = TrendingRepositoryMapperImpl(languageMapper)

    @Test
    fun `verify a contract repository is mapped to domain repository`() {
        val repository = mapper(givenContractRepository)

        val expected = Repository(
            id = GIVEN_ID,
            name = GIVEN_NAME,
            description = GIVEN_DESCRIPTION,
            ownerName = GIVEN_OWNER_LOGIN,
            ownerAvatarUrl = GIVEN_OWNER_AVATAR_URL,
            stargazerCount = GIVEN_STARGAZER_COUNT,
            forkCount = GIVEN_FORK_COUNT,
            issueCount = GIVEN_ISSUE_COUNT,
            pullRequestCount = GIVEN_PULL_REQUEST_COUNT,
            discussionCount = GIVEN_DISCUSSION_COUNT,
            url = GIVEN_URL,
            homepageUrl = GIVEN_HOMEPAGE_URL,
            openGraphImageUrl = GIVEN_OPEN_GRAPH_IMAGE_URL,
            languages = emptyList(),
        )

        repository shouldBeEqual expected
    }

    @Test
    fun `verify languages are mapped to domain and sorted by their weight`() {
        val totalWeight = 6
        val givenLanguage1 = mockk<ContractLanguage>().apply { every { size } returns 1 }
        val givenLanguage2 = mockk<ContractLanguage>().apply { every { size } returns 2 }
        val givenLanguage3 = mockk<ContractLanguage>().apply { every { size } returns 3 }

        val contractRepository = givenContractRepository
            .copy(languages = listOf(givenLanguage1, givenLanguage2, givenLanguage3))

        val expectedMediumLanguage = givenLanguage.copy(weight = 0.4F)
        val expectedLargeLanguage = givenLanguage.copy(weight = 0.5F)
        val expectedSmallLanguage = givenLanguage.copy(weight = 0.1F)

        every { languageMapper.map(totalWeight, givenLanguage1) } returns expectedMediumLanguage
        every { languageMapper.map(totalWeight, givenLanguage2) } returns expectedLargeLanguage
        every { languageMapper.map(totalWeight, givenLanguage3) } returns expectedSmallLanguage

        val repository = mapper(contractRepository)

        repository.languages shouldContainInOrder listOf(
            expectedSmallLanguage,
            expectedMediumLanguage,
            expectedLargeLanguage,
        )
    }

    companion object {
        private const val GIVEN_ID = "1"
        private const val GIVEN_NAME = "test-repo"
        private const val GIVEN_DESCRIPTION = "A test repository"
        private const val GIVEN_OWNER_LOGIN = "test-owner"
        private const val GIVEN_OWNER_AVATAR_URL = "https://test.com/avatar.png"
        private const val GIVEN_STARGAZER_COUNT = 100
        private const val GIVEN_FORK_COUNT = 50
        private const val GIVEN_ISSUE_COUNT = 10
        private const val GIVEN_PULL_REQUEST_COUNT = 5
        private const val GIVEN_DISCUSSION_COUNT = 2
        private const val GIVEN_URL = "https://test.com/repo"
        private const val GIVEN_HOMEPAGE_URL = "https://test.com"
        private const val GIVEN_OPEN_GRAPH_IMAGE_URL = "https://test.com/og-image.png"

        private val givenContractRepository = ContractRepository(
            id = GIVEN_ID,
            name = GIVEN_NAME,
            description = GIVEN_DESCRIPTION,
            ownerLogin = GIVEN_OWNER_LOGIN,
            ownerAvatarUrl = GIVEN_OWNER_AVATAR_URL,
            stargazerCount = GIVEN_STARGAZER_COUNT,
            forkCount = GIVEN_FORK_COUNT,
            issuesCount = GIVEN_ISSUE_COUNT,
            pullRequestsCount = GIVEN_PULL_REQUEST_COUNT,
            discussionsCount = GIVEN_DISCUSSION_COUNT,
            url = GIVEN_URL,
            homepageUrl = GIVEN_HOMEPAGE_URL,
            openGraphImageUrl = GIVEN_OPEN_GRAPH_IMAGE_URL,
            languages = emptyList(),
        )

        private val givenLanguage = Language("", DefaultColor, 0F, 0)
    }
}
