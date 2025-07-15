package com.github.app.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.github.app.core.ui.theme.Token.Neutral10
import com.github.app.core.ui.theme.Token.Neutral100
import com.github.app.core.ui.theme.Token.Neutral20
import com.github.app.core.ui.theme.Token.Neutral30
import com.github.app.core.ui.theme.Token.Neutral50
import com.github.app.core.ui.theme.Token.Neutral70
import com.github.app.core.ui.theme.Token.Neutral90
import com.github.app.core.ui.theme.Token.Neutral98

val LocalColors = staticCompositionLocalOf { lightGithubAppColors }

data class GithubAppColors(
    val brandPrimary: Color,
    val brandSecondary: Color,
    val onBrandPrimary: Color,
    val onBrandSecondary: Color,
    val containerPurple: Color,
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceSecondary: Color,
    val onSurfaceDiscrete: Color,
)

val lightGithubAppColors = GithubAppColors(
    brandPrimary = Color(0xFFAF38DA),
    brandSecondary = Color(0xFFE20057),
    onBrandPrimary = Neutral100,
    onBrandSecondary = Neutral100,
    containerPurple = Color(0xFF5A0073),
    background = Neutral98,
    surface = Neutral100,
    onSurface = Neutral10,
    onSurfaceSecondary = Neutral30,
    onSurfaceDiscrete = Neutral50
)

val darkGithubAppColors = GithubAppColors(
    brandPrimary = Color(0xFFE9B3FF),
    brandSecondary = Color(0xFFFFB2CD),
    onBrandPrimary = Color(0xFF330047),
    onBrandSecondary = Color(0xFF4A001F),
    containerPurple = Color(0xFFB566D1),
    background = Neutral10,
    surface = Neutral10,
    onSurface = Neutral90,
    onSurfaceSecondary = Neutral70,
    onSurfaceDiscrete = Neutral50,
)

internal object Token {
    val Neutral0 = Color.Black
    val Neutral10 = Color(0xFF1D1B20)
    val Neutral20 = Color(0xFF322F35)
    val Neutral30 = Color(0xFF48464C)
    val Neutral40 = Color(0xFF605D64)
    val Neutral50 = Color(0xFF79767D)
    val Neutral60 = Color(0xFF938F96)
    val Neutral70 = Color(0xFFAEABAF)
    val Neutral80 = Color(0xFFCAC5CD)
    val Neutral90 = Color(0xFFE6E0E9)
    val Neutral100 = Color.White

    val Neutral92 = Color(red = 236, green = 230, blue = 240)
    val Neutral94 = Color(red = 243, green = 237, blue = 247)
    val Neutral95 = Color(red = 245, green = 239, blue = 247)
    val Neutral96 = Color(red = 247, green = 242, blue = 250)
    val Neutral98 = Color(0xFFFAFAFA)
    val Neutral99 = Color(red = 255, green = 251, blue = 255)
}
