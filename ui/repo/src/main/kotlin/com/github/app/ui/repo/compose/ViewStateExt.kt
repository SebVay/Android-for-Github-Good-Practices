package com.github.app.ui.repo.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.github.app.core.ui.theme.LocalColors
import com.github.app.ui.repo.screen.state.LanguageColorViewState

/**
 * Converts a [LanguageColorViewState] to a Jetpack Compose [Color].
 *
 * This function handles the conversion of different language color states to their corresponding
 * Compose Color representation.
 *
 * - For [LanguageColorViewState.CustomColor], it parses the `hexColor` string and creates a `Color` object.
 *   The `remember` block ensures that the color is only created once and recomposed efficiently.
 * - For [LanguageColorViewState.DefaultColor], it returns the `brandPrimary` color from the current
 *   `LocalColors` theme.
 *
 * @return The Jetpack Compose [Color] representation of the language color.
 */
@Composable
internal fun LanguageColorViewState.toComposeColor(): Color = when (this) {
    is LanguageColorViewState.CustomColor -> remember { Color(hexColor.toColorInt()) }
    LanguageColorViewState.DefaultColor -> LocalColors.current.brandPrimary
}
