package com.github.app.domain.repo.usecase

import com.github.app.domain.contract.GetTrendingRepositories
import com.github.app.domain.exception.recoverWithDomainException
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.repo.usecase.mapper.TrendingRepositoryMapper

interface GetTrendingRepositoriesUseCase : suspend () -> Result<List<Repository>>

internal class GetTrendingRepositoriesUseCaseImpl(
    private val getTrendingRepositories: GetTrendingRepositories,
    private val mapper: TrendingRepositoryMapper,
) : GetTrendingRepositoriesUseCase {
    override suspend fun invoke(): Result<List<Repository>> = runCatching {
        getTrendingRepositories().map(mapper)
    }.recoverWithDomainException(REPOSITORIES_ERROR)
}

private const val REPOSITORIES_ERROR = "An error occurred while loading the trending repositories."
