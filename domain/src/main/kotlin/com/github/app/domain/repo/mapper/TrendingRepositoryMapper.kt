package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.contract.Language as ContractLanguage
import com.github.app.domain.contract.Repository as ContractRepository

internal interface TrendingRepositoryMapper : (ContractRepository) -> Repository

internal class TrendingRepositoryMapperImpl : TrendingRepositoryMapper {
    override fun invoke(contractRepository: ContractRepository): Repository {
        val languages = contractRepository.languages
        val totalBytes = languages.sumOf(ContractLanguage::size)

        return Repository(
            id = contractRepository.id,
            name = contractRepository.name,
            description = contractRepository.description.orEmpty(),
            ownerName = contractRepository.ownerLogin,
            ownerAvatarUrl = contractRepository.ownerAvatarUrl,
            stargazerCount = contractRepository.stargazerCount,
            forkCount = contractRepository.forkCount,
            issuesCount = contractRepository.issuesCount,
            pullRequestsCount = contractRepository.pullRequestsCount,
            discussionsCount = contractRepository.discussionsCount,
            url = contractRepository.url,
            homepageUrl = contractRepository.homepageUrl,
            openGraphImageUrl = contractRepository.openGraphImageUrl,
            languages = languages.map { contractLanguage ->
                Language(
                    name = contractLanguage.name,
                    color = contractLanguage.asColor(),
                    weight = contractLanguage.asWeight(totalBytes),
                    lineOfCode = contractLanguage.size,
                )
            },
        )
    }

    private fun ContractLanguage.asColor(): LanguageColor {
        return when (val color = color) {
            null -> LanguageColor.DefaultColor
            else -> LanguageColor.CustomColor(color)
        }
    }

    private fun ContractLanguage.asWeight(totalBytes: Int): Float {
        return size.toFloat() / totalBytes
    }
}
