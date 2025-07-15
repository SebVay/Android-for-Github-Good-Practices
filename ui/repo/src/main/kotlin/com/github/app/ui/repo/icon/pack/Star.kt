package com.github.app.ui.repo.icon.pack

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
import com.github.app.ui.repo.icon.RepositoryIcons

val RepositoryIcons.Star: ImageVector by lazy {
    Builder(
        name = "Star",
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
            moveToRelative(354.0f, 673.0f)
            lineToRelative(126.0f, -76.0f)
            lineToRelative(126.0f, 77.0f)
            lineToRelative(-33.0f, -144.0f)
            lineToRelative(111.0f, -96.0f)
            lineToRelative(-146.0f, -13.0f)
            lineToRelative(-58.0f, -136.0f)
            lineToRelative(-58.0f, 135.0f)
            lineToRelative(-146.0f, 13.0f)
            lineToRelative(111.0f, 97.0f)
            lineToRelative(-33.0f, 143.0f)
            close()
            moveTo(480.0f, 630.0f)
            lineTo(341.0f, 714.0f)
            quadToRelative(-5.0f, 2.0f, -8.5f, 1.5f)
            reflectiveQuadTo(325.0f, 713.0f)
            quadToRelative(-4.0f, -2.0f, -6.0f, -6.5f)
            reflectiveQuadToRelative(0.0f, -9.5f)
            lineToRelative(37.0f, -157.0f)
            lineToRelative(-122.0f, -106.0f)
            quadToRelative(-4.0f, -3.0f, -5.5f, -7.5f)
            reflectiveQuadToRelative(0.5f, -8.5f)
            quadToRelative(2.0f, -4.0f, 5.0f, -6.5f)
            reflectiveQuadToRelative(8.0f, -3.5f)
            lineToRelative(161.0f, -14.0f)
            lineToRelative(63.0f, -149.0f)
            quadToRelative(2.0f, -5.0f, 5.5f, -7.0f)
            reflectiveQuadToRelative(8.5f, -2.0f)
            quadToRelative(5.0f, 0.0f, 8.5f, 2.0f)
            reflectiveQuadToRelative(5.5f, 7.0f)
            lineToRelative(63.0f, 149.0f)
            lineToRelative(161.0f, 14.0f)
            quadToRelative(5.0f, 1.0f, 8.0f, 3.5f)
            reflectiveQuadToRelative(5.0f, 6.5f)
            quadToRelative(2.0f, 4.0f, 0.5f, 8.5f)
            reflectiveQuadTo(726.0f, 434.0f)
            lineTo(604.0f, 540.0f)
            lineToRelative(37.0f, 157.0f)
            quadToRelative(2.0f, 5.0f, 0.0f, 9.5f)
            reflectiveQuadToRelative(-6.0f, 6.5f)
            quadToRelative(-4.0f, 2.0f, -7.5f, 2.5f)
            reflectiveQuadTo(619.0f, 714.0f)
            lineToRelative(-139.0f, -84.0f)
            close()
            moveTo(480.0f, 490.0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = RepositoryIcons.Star, contentDescription = "")
    }
}
