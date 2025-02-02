package vip.cdms.weru.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("DuplicatedCode")
val Icons.Rounded.Extension by lazy {
    Builder(
        name = "Rounded.Extension",
        defaultWidth = 24.0.dp,
        defaultHeight = 24.0.dp,
        viewportWidth = 960.0f,
        viewportHeight = 960.0f
    ).apply {
        path(
            fill = SolidColor(Color.Black),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(352.0f, 840.0f)
            lineTo(200.0f, 840.0f)
            quadToRelative(-33.0f, 0.0f, -56.5f, -23.5f)
            reflectiveQuadTo(120.0f, 760.0f)
            verticalLineToRelative(-152.0f)
            quadToRelative(48.0f, 0.0f, 84.0f, -30.5f)
            reflectiveQuadToRelative(36.0f, -77.5f)
            quadToRelative(0.0f, -47.0f, -36.0f, -77.5f)
            reflectiveQuadTo(120.0f, 392.0f)
            verticalLineToRelative(-152.0f)
            quadToRelative(0.0f, -33.0f, 23.5f, -56.5f)
            reflectiveQuadTo(200.0f, 160.0f)
            horizontalLineToRelative(160.0f)
            quadToRelative(0.0f, -42.0f, 29.0f, -71.0f)
            reflectiveQuadToRelative(71.0f, -29.0f)
            quadToRelative(42.0f, 0.0f, 71.0f, 29.0f)
            reflectiveQuadToRelative(29.0f, 71.0f)
            horizontalLineToRelative(160.0f)
            quadToRelative(33.0f, 0.0f, 56.5f, 23.5f)
            reflectiveQuadTo(800.0f, 240.0f)
            verticalLineToRelative(160.0f)
            quadToRelative(42.0f, 0.0f, 71.0f, 29.0f)
            reflectiveQuadToRelative(29.0f, 71.0f)
            quadToRelative(0.0f, 42.0f, -29.0f, 71.0f)
            reflectiveQuadToRelative(-71.0f, 29.0f)
            verticalLineToRelative(160.0f)
            quadToRelative(0.0f, 33.0f, -23.5f, 56.5f)
            reflectiveQuadTo(720.0f, 840.0f)
            lineTo(568.0f, 840.0f)
            quadToRelative(0.0f, -50.0f, -31.5f, -85.0f)
            reflectiveQuadTo(460.0f, 720.0f)
            quadToRelative(-45.0f, 0.0f, -76.5f, 35.0f)
            reflectiveQuadTo(352.0f, 840.0f)
            close()
        }
    }.build()
}
