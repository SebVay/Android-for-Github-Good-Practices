package com.github.app.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = lightGithubAppColors.brandPrimary,
    onPrimary = lightGithubAppColors.onBrandPrimary,
    secondary = lightGithubAppColors.brandSecondary,
    onSecondary = lightGithubAppColors.onBrandSecondary,
    background = lightGithubAppColors.background,
    surface = lightGithubAppColors.surface,
    onSurface = lightGithubAppColors.onSurface,
    onSurfaceVariant = lightGithubAppColors.onSurfaceSecondary,
)

private val DarkColorScheme = darkColorScheme(
    primary = darkGithubAppColors.brandPrimary,
    onPrimary = darkGithubAppColors.onBrandPrimary,
    secondary = darkGithubAppColors.brandSecondary,
    onSecondary = darkGithubAppColors.onBrandSecondary,
    background = darkGithubAppColors.background,
    surface = darkGithubAppColors.surface,
    onSurface = darkGithubAppColors.onSurface,
    onSurfaceVariant = lightGithubAppColors.onSurfaceSecondary,
)

@Composable
fun GithubAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val githubAppColors = if (darkTheme) darkGithubAppColors else lightGithubAppColors
    val materialColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalColors provides githubAppColors,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = GithubAppTypography,
            content = content,
        )
    }
}
