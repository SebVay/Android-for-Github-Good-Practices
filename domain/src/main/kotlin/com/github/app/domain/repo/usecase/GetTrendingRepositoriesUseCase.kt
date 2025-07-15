package com.github.app.domain.repo.usecase

import com.github.app.domain.contract.GetTrendingRepositories
import com.github.app.domain.ext.runRecovering
import com.github.app.domain.repo.mapper.TrendingRepositoryMapper
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.repo.usecase.TimePeriod.LAST_MONTH
import com.github.app.domain.repo.usecase.TimePeriod.LAST_WEEK
import com.github.app.domain.repo.usecase.TimePeriod.YESTERDAY
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun interface GetTrendingRepositoriesUseCase : suspend (TimePeriod) -> Result<List<Repository>>

internal class GetTrendingRepositoriesUseCaseImpl(
    private val getTrendingRepositories: GetTrendingRepositories,
    private val mapper: TrendingRepositoryMapper,
    private val clock: Clock,
) : GetTrendingRepositoriesUseCase {

    override suspend fun invoke(timePeriod: TimePeriod): Result<List<Repository>> {
        return runRecovering(REPOSITORIES_ERROR) {
            getTrendingRepositories(timePeriod.toQuery()).map(mapper)
        }
    }

    private fun TimePeriod.toQuery(): String {
        return buildString {
            append("created:>" + periodQueried())
            append(" sort:stars")
        }
    }

    private fun TimePeriod.periodQueried(): String {
        val period = when (this) {
            YESTERDAY -> DateTimePeriod(days = 1)
            LAST_WEEK -> DateTimePeriod(days = 7)
            LAST_MONTH -> DateTimePeriod(months = 1)
        }

        return clock.now()
            .minus(period, TimeZone.UTC)
            .toLocalDateTime(TimeZone.UTC)
            .let { FORMATTER.format(it) }
    }

    companion object {
        private val FORMATTER = LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            day()
        }

        private const val REPOSITORIES_ERROR =
            "An error occurred while loading the trending repositories."
    }
}

/**
 * Represents the time period for which trending repositories are fetched.
 */
enum class TimePeriod {
    YESTERDAY, LAST_WEEK, LAST_MONTH
}