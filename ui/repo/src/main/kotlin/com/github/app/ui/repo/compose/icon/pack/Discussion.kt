package com.github.app.ui.repo.compose.icon.pack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.app.ui.repo.compose.icon.RepositoryIcons

val RepositoryIcons.Discussion: ImageVector by lazy {
    Builder(
        name = "Discussion",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f,
    ).apply {
        path(
            fill = SolidColor(Color(0xFFe3e3e3)),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero,
        ) {
            moveTo(808.0f, 668.0f)
            quadToRelative(0.0f, 20.0f, -18.5f, 27.5f)
            reflectiveQuadTo(757.0f, 689.0f)
            lineToRelative(-61.0f, -61.0f)
            lineTo(320.0f, 628.0f)
            quadToRelative(-23.1f, 0.0f, -39.55f, -16.45f)
            quadTo(264.0f, 595.1f, 264.0f, 572.0f)
            horizontalLineToRelative(428.0f)
            quadToRelative(24.75f, 0.0f, 42.38f, -17.63f)
            quadTo(752.0f, 536.75f, 752.0f, 512.0f)
            verticalLineToRelative(-272.0f)
            quadToRelative(23.1f, 0.0f, 39.55f, 16.45f)
            quadTo(808.0f, 272.9f, 808.0f, 296.0f)
            verticalLineToRelative(372.0f)
            close()
            moveTo(180.0f, 536.0f)
            lineToRelative(72.0f, -72.0f)
            horizontalLineToRelative(360.0f)
            quadToRelative(14.0f, 0.0f, 23.0f, -9.0f)
            reflectiveQuadToRelative(9.0f, -23.0f)
            verticalLineToRelative(-240.0f)
            quadToRelative(0.0f, -14.0f, -9.0f, -23.0f)
            reflectiveQuadToRelative(-23.0f, -9.0f)
            lineTo(212.0f, 160.0f)
            quadToRelative(-14.0f, 0.0f, -23.0f, 9.0f)
            reflectiveQuadToRelative(-9.0f, 23.0f)
            verticalLineToRelative(344.0f)
            close()
            moveTo(181.67f, 562.0f)
            quadToRelative(-10.67f, 0.0f, -20.17f, -8.0f)
            quadToRelative(-9.5f, -8.0f, -9.5f, -22.45f)
            lineTo(152.0f, 192.0f)
            quadToRelative(0.0f, -24.75f, 17.63f, -42.38f)
            quadTo(187.25f, 132.0f, 212.0f, 132.0f)
            horizontalLineToRelative(400.0f)
            quadToRelative(24.75f, 0.0f, 42.38f, 17.62f)
            quadTo(672.0f, 167.25f, 672.0f, 192.0f)
            verticalLineToRelative(240.0f)
            quadToRelative(0.0f, 24.75f, -17.62f, 42.37f)
            quadTo(636.75f, 492.0f, 612.0f, 492.0f)
            lineTo(264.0f, 492.0f)
            lineToRelative(-61.0f, 61.0f)
            quadToRelative(-5.0f, 5.0f, -10.5f, 7.0f)
            reflectiveQuadToRelative(-10.83f, 2.0f)
            close()
            moveTo(180.0f, 432.0f)
            verticalLineToRelative(-272.0f)
            verticalLineToRelative(272.0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = RepositoryIcons.Discussion, contentDescription = "")
    }
}
