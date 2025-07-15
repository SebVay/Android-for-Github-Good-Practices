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

val RepositoryIcons.Error: ImageVector by lazy {
    Builder(
        name = "Error", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
        viewportWidth = 960.0f, viewportHeight = 960.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(480.0f, 652.0f)
            quadToRelative(8.5f, 0.0f, 14.25f, -5.75f)
            reflectiveQuadTo(500.0f, 632.0f)
            quadToRelative(0.0f, -8.5f, -5.75f, -14.25f)
            reflectiveQuadTo(480.0f, 612.0f)
            quadToRelative(-8.5f, 0.0f, -14.25f, 5.75f)
            reflectiveQuadTo(460.0f, 632.0f)
            quadToRelative(0.0f, 8.5f, 5.75f, 14.25f)
            reflectiveQuadTo(480.0f, 652.0f)
            close()
            moveTo(480.04f, 528.0f)
            quadToRelative(5.96f, 0.0f, 9.96f, -4.02f)
            quadToRelative(4.0f, -4.03f, 4.0f, -9.98f)
            verticalLineToRelative(-212.0f)
            quadToRelative(0.0f, -5.95f, -4.04f, -9.97f)
            quadToRelative(-4.03f, -4.03f, -10.0f, -4.03f)
            quadToRelative(-5.96f, 0.0f, -9.96f, 4.03f)
            quadToRelative(-4.0f, 4.02f, -4.0f, 9.97f)
            verticalLineToRelative(212.0f)
            quadToRelative(0.0f, 5.95f, 4.04f, 9.98f)
            quadToRelative(4.03f, 4.02f, 10.0f, 4.02f)
            close()
            moveTo(480.17f, 828.0f)
            quadToRelative(-72.17f, 0.0f, -135.73f, -27.39f)
            quadToRelative(-63.56f, -27.39f, -110.57f, -74.35f)
            quadToRelative(-47.02f, -46.96f, -74.44f, -110.43f)
            quadTo(132.0f, 552.35f, 132.0f, 480.17f)
            quadToRelative(0.0f, -72.17f, 27.39f, -135.73f)
            quadToRelative(27.39f, -63.56f, 74.35f, -110.57f)
            quadToRelative(46.96f, -47.02f, 110.43f, -74.44f)
            quadTo(407.65f, 132.0f, 479.83f, 132.0f)
            quadToRelative(72.17f, 0.0f, 135.73f, 27.39f)
            quadToRelative(63.56f, 27.39f, 110.57f, 74.35f)
            quadToRelative(47.02f, 46.96f, 74.44f, 110.43f)
            quadTo(828.0f, 407.65f, 828.0f, 479.83f)
            quadToRelative(0.0f, 72.17f, -27.39f, 135.73f)
            quadToRelative(-27.39f, 63.56f, -74.35f, 110.57f)
            quadToRelative(-46.96f, 47.02f, -110.43f, 74.44f)
            quadTo(552.35f, 828.0f, 480.17f, 828.0f)
            close()
            moveTo(480.0f, 800.0f)
            quadToRelative(134.0f, 0.0f, 227.0f, -93.0f)
            reflectiveQuadToRelative(93.0f, -227.0f)
            quadToRelative(0.0f, -134.0f, -93.0f, -227.0f)
            reflectiveQuadToRelative(-227.0f, -93.0f)
            quadToRelative(-134.0f, 0.0f, -227.0f, 93.0f)
            reflectiveQuadToRelative(-93.0f, 227.0f)
            quadToRelative(0.0f, 134.0f, 93.0f, 227.0f)
            reflectiveQuadToRelative(227.0f, 93.0f)
            close()
            moveTo(480.0f, 480.0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = RepositoryIcons.Error, contentDescription = "")
    }
}
