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

val RepositoryIcons.PullRequest: ImageVector by lazy {
    Builder(
        name =
        "PullRequest",
        defaultWidth = 24.0.dp,
        defaultHeight =
        24.0.dp,
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
            moveTo(480.0f, 654.0f)
            quadToRelative(-69.0f, 0.0f, -117.0f, -45.5f)
            reflectiveQuadTo(305.0f, 494.0f)
            lineTo(146.0f, 494.0f)
            quadToRelative(-5.95f, 0.0f, -9.98f, -4.04f)
            quadToRelative(-4.02f, -4.03f, -4.02f, -10.0f)
            quadToRelative(0.0f, -5.96f, 4.02f, -9.96f)
            quadToRelative(4.03f, -4.0f, 9.98f, -4.0f)
            horizontalLineToRelative(159.0f)
            quadToRelative(10.0f, -69.0f, 58.0f, -114.5f)
            reflectiveQuadTo(480.0f, 306.0f)
            quadToRelative(69.0f, 0.0f, 117.5f, 45.5f)
            reflectiveQuadTo(655.0f, 466.0f)
            horizontalLineToRelative(159.0f)
            quadToRelative(5.95f, 0.0f, 9.97f, 4.04f)
            quadToRelative(4.03f, 4.03f, 4.03f, 10.0f)
            quadToRelative(0.0f, 5.96f, -4.03f, 9.96f)
            quadToRelative(-4.02f, 4.0f, -9.97f, 4.0f)
            lineTo(655.0f, 494.0f)
            quadToRelative(-9.0f, 69.0f, -57.5f, 114.5f)
            reflectiveQuadTo(480.0f, 654.0f)
            close()
            moveTo(480.0f, 626.0f)
            quadToRelative(60.0f, 0.0f, 103.0f, -43.0f)
            reflectiveQuadToRelative(43.0f, -103.0f)
            quadToRelative(0.0f, -60.0f, -43.0f, -103.0f)
            reflectiveQuadToRelative(-103.0f, -43.0f)
            quadToRelative(-60.0f, 0.0f, -103.0f, 43.0f)
            reflectiveQuadToRelative(-43.0f, 103.0f)
            quadToRelative(0.0f, 60.0f, 43.0f, 103.0f)
            reflectiveQuadToRelative(103.0f, 43.0f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(
            imageVector = RepositoryIcons.PullRequest,
            contentDescription = "",
        )
    }
}
