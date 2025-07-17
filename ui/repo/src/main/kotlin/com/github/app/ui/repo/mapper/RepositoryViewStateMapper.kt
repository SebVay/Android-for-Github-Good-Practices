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
 * Maps a list of [Repository] domain models to a list of [RepositoryViewState] UI models.
 */
internal interface RepositoryViewStateMapper : (List<Repository>) -> List<RepositoryViewState>

internal class RepositoryViewStateMapperImpl : RepositoryViewStateMapper {
    override fun invoke(repositories: List<Repository>): List<RepositoryViewState> {
        return repositories.map { repository ->
            RepositoryViewState(
                id = repository.id,
                imageUrl = repository.ownerAvatarUrl,
                name = repository.name,
                description = repository.description,
                stargazerCount = repository.stargazerCount,
                forkCount = repository.forkCount,
                issuesCount = repository.issuesCount,
                pullRequestsCount = repository.pullRequestsCount,
                discussionsCount = repository.discussionsCount,
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
        }.toImmutableList()
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
            val map: List<LanguageProgressionViewState> = map { language ->
                LanguageProgressionViewState(language.toColor(), lastWeight)
                    .also { lastWeight += language.weight }
            }

            LanguageLineViewState(map.toImmutableList())
        } else {
            null
        }
    }

    private fun Language.toColor(): LanguageColorViewState = when (val color = color) {
        is CustomColor -> LanguageColorViewState.CustomColor(color.hexColor)
        DefaultColor -> LanguageColorViewState.DefaultColor
    }
}
