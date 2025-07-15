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

val RepositoryIcons.Fork: ImageVector by lazy {
    Builder(
        name = "Fork", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
        viewportWidth = 960.0f, viewportHeight = 960.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFe3e3e3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(466.0f, 814.0f)
            verticalLineToRelative(-119.0f)
            quadToRelative(0.0f, -54.0f, -15.0f, -87.0f)
            reflectiveQuadToRelative(-47.0f, -60.0f)
            lineToRelative(20.0f, -20.0f)
            quadToRelative(14.0f, 13.0f, 29.5f, 33.5f)
            reflectiveQuadTo(480.0f, 605.0f)
            quadToRelative(11.0f, -26.0f, 31.5f, -50.5f)
            reflectiveQuadTo(555.0f, 510.0f)
            quadToRelative(54.0f, -47.0f, 83.0f, -118.0f)
            reflectiveQuadToRelative(27.0f, -205.0f)
            lineToRelative(-79.0f, 79.0f)
            quadToRelative(-4.0f, 4.0f, -9.5f, 4.5f)
            reflectiveQuadTo(566.0f, 266.0f)
            quadToRelative(-5.0f, -5.0f, -5.0f, -10.0f)
            reflectiveQuadToRelative(5.0f, -10.0f)
            lineToRelative(93.0f, -93.0f)
            quadToRelative(5.0f, -5.0f, 10.0f, -7.0f)
            reflectiveQuadToRelative(11.0f, -2.0f)
            quadToRelative(6.0f, 0.0f, 11.0f, 2.0f)
            reflectiveQuadToRelative(10.0f, 7.0f)
            lineToRelative(93.0f, 93.0f)
            quadToRelative(4.0f, 4.0f, 4.5f, 9.5f)
            reflectiveQuadTo(794.0f, 266.0f)
            quadToRelative(-5.0f, 5.0f, -10.0f, 5.0f)
            reflectiveQuadToRelative(-10.0f, -5.0f)
            lineToRelative(-81.0f, -81.0f)
            quadToRelative(2.0f, 132.0f, -27.5f, 210.5f)
            reflectiveQuadTo(575.0f, 530.0f)
            quadToRelative(-35.0f, 32.0f, -58.0f, 64.0f)
            reflectiveQuadToRelative(-23.0f, 101.0f)
            verticalLineToRelative(119.0f)
            quadToRelative(0.0f, 6.0f, -4.0f, 10.0f)
            reflectiveQuadToRelative(-10.0f, 4.0f)
            quadToRelative(-6.0f, 0.0f, -10.0f, -4.0f)
            reflectiveQuadToRelative(-4.0f, -10.0f)
            close()
            moveTo(277.0f, 324.0f)
            quadToRelative(-5.0f, -21.0f, -7.5f, -61.0f)
            reflectiveQuadToRelative(-2.5f, -78.0f)
            lineToRelative(-81.0f, 81.0f)
            quadToRelative(-4.0f, 4.0f, -9.5f, 4.5f)
            reflectiveQuadTo(166.0f, 266.0f)
            quadToRelative(-5.0f, -5.0f, -5.0f, -10.0f)
            reflectiveQuadToRelative(5.0f, -10.0f)
            lineToRelative(93.0f, -93.0f)
            quadToRelative(5.0f, -5.0f, 10.0f, -7.0f)
            reflectiveQuadToRelative(11.0f, -2.0f)
            quadToRelative(6.0f, 0.0f, 11.0f, 2.0f)
            reflectiveQuadToRelative(10.0f, 7.0f)
            lineToRelative(93.0f, 93.0f)
            quadToRelative(4.0f, 4.0f, 4.5f, 9.5f)
            reflectiveQuadTo(394.0f, 266.0f)
            quadToRelative(-5.0f, 5.0f, -10.0f, 5.0f)
            reflectiveQuadToRelative(-10.0f, -5.0f)
            lineToRelative(-79.0f, -79.0f)
            quadToRelative(-1.0f, 37.0f, 1.5f, 73.0f)
            reflectiveQuadToRelative(7.5f, 58.0f)
            lineToRelative(-27.0f, 6.0f)
            close()
            moveTo(348.0f, 491.0f)
            quadToRelative(-15.0f, -19.0f, -29.5f, -44.5f)
            reflectiveQuadTo(297.0f, 401.0f)
            lineToRelative(27.0f, -7.0f)
            quadToRelative(6.0f, 17.0f, 17.5f, 38.5f)
            reflectiveQuadTo(368.0f, 471.0f)
            lineToRelative(-20.0f, 20.0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = RepositoryIcons.Fork, contentDescription = "")
    }
}
