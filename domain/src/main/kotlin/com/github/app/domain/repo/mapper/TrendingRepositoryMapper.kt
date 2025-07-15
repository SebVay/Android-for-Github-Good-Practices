package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.Repository
import kotlin.math.roundToInt
import com.github.app.domain.contract.Language as ContractLanguage
import com.github.app.domain.contract.Repository as ContractRepository

internal interface TrendingRepositoryMapper : (ContractRepository) -> Repository

internal class TrendingRepositoryMapperImpl : TrendingRepositoryMapper {
    override fun invoke(contractRepository: ContractRepository): Repository {
        val languages = contractRepository.languages
        val totalLineOfCode = languages.sumOf(ContractLanguage::size)

        return Repository(
            name = contractRepository.name,
            description = contractRepository.description.orEmpty(),
            ownerName = contractRepository.ownerLogin,
            ownerAvatarUrl = contractRepository.ownerAvatarUrl,
            stargazerCount = contractRepository.stargazerCount,
            forkCount = contractRepository.forkCount,
            issuesCount = contractRepository.issuesCount,
            pullRequestsCount = contractRepository.pullRequestsCount,
            url = contractRepository.url,
            homepageUrl = contractRepository.homepageUrl,
            openGraphImageUrl = contractRepository.openGraphImageUrl,
            languages = languages.map { contractLanguage ->
                Language(
                    name = contractLanguage.name,
                    color = contractLanguage.color,
                    percentage = contractLanguage.asPercentage(totalLineOfCode),
                    lineOfCode = contractLanguage.size,
                )
            },
        )
    }

    @Suppress("MagicNumber")
    private fun ContractLanguage.asPercentage(totalLineOfCode: Int): Int {
        return (size.toFloat().div(totalLineOfCode.toFloat()))
            .times(100)
            .roundToInt()
    }
}
