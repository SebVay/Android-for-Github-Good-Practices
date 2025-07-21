package com.github.app.domain.repo.usecase

import com.github.app.domain.contract.GetTrendingRepositories
import com.github.app.domain.exception.DomainException
import com.github.app.domain.repo.mapper.TrendingRepositoryMapper
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.repo.usecase.TimePeriod.LAST_MONTH
import com.github.app.domain.repo.usecase.TimePeriod.LAST_WEEK
import com.github.app.domain.repo.usecase.TimePeriod.YESTERDAY
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.time.Clock
import kotlin.time.Instant
import com.github.app.domain.contract.Repository as ContractRepository

class GetTrendingRepositoriesUseCaseImplTest {

    private val getTrendingRepositories = mockk<GetTrendingRepositories>()
    private val mapper = mockk<TrendingRepositoryMapper>()
    private val clock = mockk<Clock>()

    private val useCase = GetTrendingRepositoriesUseCaseImpl(
        getTrendingRepositories = getTrendingRepositories,
        mapper = mapper,
        clock = clock,
    )

    @Test
    fun `verify yesterday's repositories are returned`() {
        runTest {
            val givenContractRepository = mockk<ContractRepository>()
            val expectedRepository = mockk<Repository>()

            every { clock.now() } returns Instant.parse(ANY_TIME)
            coEvery { getTrendingRepositories(any()) } returns listOf(givenContractRepository)
            every { mapper(givenContractRepository) } returns expectedRepository

            useCase(YESTERDAY) shouldBeSuccess listOf(expectedRepository)
        }
    }

    @Test
    fun `verify yesterday's query is well formed`() = runTest {
        val givenQuery = "created:>2023-03-21 sort:stars"

        every { clock.now() } returns Instant.parse("2023-03-22T12:00:00Z")
        coEvery { getTrendingRepositories(givenQuery) } returns emptyList()

        useCase(YESTERDAY)

        coVerify { getTrendingRepositories(givenQuery) }
    }

    @Test
    fun `verify last week's repositories are returned`() = runTest {
        val givenContractRepository = mockk<ContractRepository>()
        val expectedRepository = mockk<Repository>()

        every { clock.now() } returns Instant.parse(ANY_TIME)
        coEvery { getTrendingRepositories(any()) } returns listOf(givenContractRepository)
        every { mapper(givenContractRepository) } returns expectedRepository

        useCase(LAST_WEEK) shouldBeSuccess listOf(expectedRepository)
    }

    @Test
    fun `verify last week query is well formed`() = runTest {
        val givenQuery = "created:>2023-03-15 sort:stars"

        every { clock.now() } returns Instant.parse("2023-03-22T12:00:00Z")
        coEvery { getTrendingRepositories(givenQuery) } returns emptyList()

        useCase(LAST_WEEK)

        coVerify { getTrendingRepositories(givenQuery) }
    }

    @Test
    fun `verify last month's repositories are returned`() = runTest {
        val givenContractRepository = mockk<ContractRepository>()
        val expectedRepository = mockk<Repository>()

        every { clock.now() } returns Instant.parse(ANY_TIME)
        coEvery { getTrendingRepositories(any()) } returns listOf(givenContractRepository)
        every { mapper(givenContractRepository) } returns expectedRepository

        useCase(LAST_MONTH) shouldBeSuccess listOf(expectedRepository)
    }

    @Test
    fun `verify last month query is well formed`() = runTest {
        val givenQuery = "created:>2023-02-22 sort:stars"

        every { clock.now() } returns Instant.parse("2023-03-22T12:00:00Z")
        coEvery { getTrendingRepositories(givenQuery) } returns emptyList()

        useCase(LAST_MONTH)

        coVerify { getTrendingRepositories(givenQuery) }
    }

    @Test
    fun `verify exception is mapped to failure when fetching repositories fails`() = runTest {
        val givenQuery = "created:>2023-03-21 sort:stars"
        val givenException = Exception()

        every { clock.now() } returns Instant.parse("2023-03-22T12:00:00Z")
        coEvery { getTrendingRepositories(givenQuery) } throws givenException

        useCase(YESTERDAY) shouldBeFailure { throwable ->
            throwable.shouldBeTypeOf<DomainException.UnknownException>()
        }
    }
}

private const val ANY_TIME = "2023-03-22T12:00:00Z"
