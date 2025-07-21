package com.github.app.domain.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.Repository
import com.github.app.domain.contract.Language as ContractLanguage
import com.github.app.domain.contract.Repository as ContractRepository

internal interface TrendingRepositoryMapper : (ContractRepository) -> Repository

internal class TrendingRepositoryMapperImpl(
    val languageMapper: LanguageMapper,
) : TrendingRepositoryMapper {
    override fun invoke(contractRepository: ContractRepository): Repository {
        val languages = contractRepository.languages
        val totalBytes = languages.sumOf(ContractLanguage::size)

        return Repository(
            id = contractRepository.id,
            name = contractRepository.name,
            description = contractRepository.description,
            ownerName = contractRepository.ownerLogin,
            ownerAvatarUrl = contractRepository.ownerAvatarUrl,
            stargazerCount = contractRepository.stargazerCount,
            forkCount = contractRepository.forkCount,
            issueCount = contractRepository.issuesCount,
            pullRequestCount = contractRepository.pullRequestsCount,
            discussionCount = contractRepository.discussionsCount,
            url = contractRepository.url,
            homepageUrl = contractRepository.homepageUrl,
            openGraphImageUrl = contractRepository.openGraphImageUrl,
            languages = languages
                .map { contractLanguage -> languageMapper.map(totalBytes, contractLanguage) }
                .sortedBy(Language::weight),
        )
    }
}
