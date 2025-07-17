package com.github.app.core.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AppTitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    AppText(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        overflow = overflow,
        maxLines = maxLines,
        textsToBold = textsToBold,
    )
}

@Composable
fun AppHeadlineLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    AppText(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineLarge,
        overflow = overflow,
        maxLines = maxLines,
        textsToBold = textsToBold,
    )
}

@Composable
fun AppBodyLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    AppText(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyLarge,
        overflow = overflow,
        maxLines = maxLines,
        textsToBold = textsToBold,
    )
}

@Composable
fun AppBodyMedium(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    AppText(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        overflow = overflow,
        maxLines = maxLines,
        textsToBold = textsToBold,
    )
}

@Composable
fun AppLabelLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    AppText(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.labelLarge,
        overflow = overflow,
        maxLines = maxLines,
        textsToBold = textsToBold,
    )
}

@Composable
internal fun AppText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textsToBold: ImmutableList<String> = persistentListOf(),
) {
    val annotatedString = buildAnnotatedString {
        append(text)

        ApplyBold(this, textsToBold, text)
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        color = color,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
    )
}

/**
 * Applies bold styling to specified parts of a text.
 *
 * @param builder The AnnotatedString.Builder to which the styled text will be appended.
 * @param textsToBold An immutable list of strings that should be rendered in bold.
 * @param text The original text string where bold styling will be applied.
 */
@Composable
private fun ApplyBold(
    builder: AnnotatedString.Builder,
    textsToBold: ImmutableList<String>,
    text: String,
) {
    for (textToBold in textsToBold) {
        val start = remember { text.indexOf(textToBold) }
        val end = remember { start + textToBold.length }

        builder.addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = start,
            end = end,
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun AppTextPreview() {
    AppText(
        text = "This is a sample text with bold words: bold and important.",
        style = MaterialTheme.typography.bodyLarge,
        textsToBold = persistentListOf("bold", "important"),
    )
}

@Preview(showBackground = true)
@Composable
internal fun AppTextWithOverflowPreview() {
    AppText(
        text = "This is a very long sample text that will likely overflow " +
            "and demonstrate the ellipsis functionality. Which is really long.",
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}
