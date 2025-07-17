package com.github.app.ui.repo.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.github.app.ui.repo.compose.attr.RepositoryDimens
import com.github.app.ui.repo.compose.toComposeColor
import com.github.app.ui.repo.screen.state.LanguageLineViewState

/**
 * Displays a horizontal line representing the language distribution of a repository.
 * The line is colored with a gradient based on the languages present and their proportions.
 *
 * @param languageLine The view state containing the language progression data.
 */
@Suppress("SpreadOperator")
@Composable
internal fun LanguageLine(languageLine: LanguageLineViewState) {
    Box(
        Modifier
            .height(RepositoryDimens.LanguageLineHeight)
            .fillMaxWidth()
            .then(
                if (languageLine.languageProgression.size > 1) {
                    Modifier.background(Brush.linearGradient(*languageLine.linearGradientArray()))
                } else {
                    Modifier.background(languageLine.languageProgression.single().color.toComposeColor())
                },
            ),
    )
}

/**
 * Converts the language progression data into an array of pairs suitable for creating a linear gradient.
 * Each pair consists of a float representing the start weight (position) of the color stop
 * and the corresponding Compose Color.
 *
 * @return An array of [Pair]<[Float], [Color]> where the first element is the stop position (0.0f to 1.0f)
 *         and the second element is the [Color] at that stop.
 */
@Composable
private fun LanguageLineViewState.linearGradientArray(): Array<Pair<Float, Color>> {
    return languageProgression.map { it.startWeight to it.color.toComposeColor() }.toTypedArray()
}
