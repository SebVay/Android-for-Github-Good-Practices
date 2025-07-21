package com.github.app.domain.repo.model

data class Repository(
    val id: String,
    val name: String,
    val description: String?,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val stargazerCount: Int,
    val forkCount: Int,
    val issueCount: Int,
    val pullRequestCount: Int,
    val discussionCount: Int,
    val url: String,
    val homepageUrl: String?,
    val openGraphImageUrl: String,
    val languages: List<Language>,
)

data class Language(
    val name: String,
    val color: LanguageColor,
    val weight: Float,
    val size: Int,
)

/**
 * Represents the color of a programming language.
 *
 * This sealed class can be used to define either a default color,
 * indicating that the application should use its predefined default color,
 * or a custom color, specified by a hexadecimal string.
 */
sealed class LanguageColor {
    /**
     * UI should fallback to its default color.
     */
    object DefaultColor : LanguageColor()

    data class CustomColor(val hexColor: String) : LanguageColor()
}
