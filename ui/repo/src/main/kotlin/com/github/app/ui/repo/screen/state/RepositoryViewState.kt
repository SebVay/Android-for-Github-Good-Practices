package com.github.app.ui.repo.screen.state

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

/**
 * Represents the view state for a Github Repository.
 */
internal data class RepositoryViewState(
    val id: String,
    val name: String,
    val authorName: String,
    val imageUrl: String,
    val description: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val pullRequestsCount: Int,
    val discussionsCount: Int,
    val languages: ImmutableList<LanguageViewState>,
    val languageLine: LanguageLineViewState?
)

@Stable
internal data class LanguageViewState(
    val name: String,
    val color: LanguageColorViewState,
    val weight: Float,
)

@Stable
internal sealed class LanguageColorViewState {
    /**
     * Fallback the default color.
     */
    object DefaultColor : LanguageColorViewState()

    class CustomColor(val hexColor: String) : LanguageColorViewState()
}

@Stable
internal data class LanguageLineViewState(
    val languageProgression: ImmutableList<LanguageProgressionViewState>
)

internal data class LanguageProgressionViewState(
    val color: LanguageColorViewState,
    val startWeight: Float
)