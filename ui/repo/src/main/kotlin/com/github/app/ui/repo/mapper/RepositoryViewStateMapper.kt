package com.github.app.ui.repo.mapper

import com.github.app.domain.repo.model.Language
import com.github.app.domain.repo.model.LanguageColor.CustomColor
import com.github.app.domain.repo.model.LanguageColor.DefaultColor
import com.github.app.domain.repo.model.Repository
import com.github.app.ui.repo.screen.state.LanguageColorViewState
import com.github.app.ui.repo.screen.state.LanguageLineViewState
import com.github.app.ui.repo.screen.state.LanguageProgressionViewState
import com.github.app.ui.repo.screen.state.LanguageViewState
import com.github.app.ui.repo.screen.state.RepositoryViewState
import kotlinx.collections.immutable.toImmutableList

/**
 * Maps a [Repository] domain model to a corresponding [RepositoryViewState] UI model.
 */
internal fun interface RepositoryViewStateMapper {
    operator fun invoke(repository: Repository): RepositoryViewState
}

internal class RepositoryViewStateMapperImpl : RepositoryViewStateMapper {
    override fun invoke(repository: Repository): RepositoryViewState {
        return RepositoryViewState(
            id = repository.id,
            imageUrl = repository.ownerAvatarUrl,
            name = repository.name,
            description = repository.description,
            stargazerCount = repository.stargazerCount,
            forkCount = repository.forkCount,
            issuesCount = repository.issueCount,
            pullRequestsCount = repository.pullRequestCount,
            discussionsCount = repository.discussionCount,
            authorName = repository.ownerName,
            languages = repository.languages.map { language ->
                LanguageViewState(
                    name = language.name,
                    color = language.toColor(),
                    weight = language.weight,
                )
            }.toImmutableList(),
            languageLine = repository.languages.asLanguageLine(),
        )
    }
}

/**
 * Transforms a list of [Language] domain models into a [LanguageLineViewState] UI model.
 *
 * This function calculates the progression of each language's weight and creates a
 * [LanguageLineViewState] to represent the language distribution.
 */
private fun List<Language>.asLanguageLine(): LanguageLineViewState? {
    return if (isNotEmpty()) {
        // Personal note:
        // Functional approach (scan, reduce) could have been used here, but this is more readable IMO
        var lastWeight = 0F

        val progressions = map { language ->
            LanguageProgressionViewState(
                color = language.toColor(),
                startWeight = lastWeight
            ).also { lastWeight += language.weight }
        }

        LanguageLineViewState(progressions.toImmutableList())
    } else {
        null
    }
}

private fun Language.toColor(): LanguageColorViewState = when (val color = color) {
    is CustomColor -> LanguageColorViewState.CustomColor(color.hexColor)
    DefaultColor -> LanguageColorViewState.DefaultColor
}

