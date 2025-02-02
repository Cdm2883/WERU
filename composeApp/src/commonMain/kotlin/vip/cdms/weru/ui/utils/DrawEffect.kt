package vip.cdms.weru.ui.utils

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Density

// ltr only

fun DrawScope.drawInnerRightRoundCorner(color: Color, shape: CornerBasedShape, density: Density) =
    clipPath(Path().apply {
        addRoundRect(RoundRect(
            Rect(Offset(size.width, 0f), size),
            topLeft = CornerRadius(shape.topStart.toPx(size, density)),
            topRight = CornerRadius(shape.topEnd.toPx(size, density)),
            bottomRight = CornerRadius(shape.bottomEnd.toPx(size, density)),
            bottomLeft = CornerRadius(shape.bottomStart.toPx(size, density)),
        ))
    }, clipOp = ClipOp.Difference) {
        drawRect(color, size = size.copy(width = size.width + shape.topStart.toPx(size, density)))
    }

fun DrawScope.drawInnerTopRoundCorner(color: Color, shape: CornerBasedShape, density: Density) =
    clipPath(Path().apply {
        addRoundRect(RoundRect(
            Rect(Offset(0f, -size.height), size),
            topLeft = CornerRadius(shape.topStart.toPx(size, density)),
            topRight = CornerRadius(shape.topEnd.toPx(size, density)),
            bottomRight = CornerRadius(shape.bottomEnd.toPx(size, density)),
            bottomLeft = CornerRadius(shape.bottomStart.toPx(size, density)),
        ))
    }, clipOp = ClipOp.Difference) {
        drawRect(color, Offset(0f, -shape.topStart.toPx(size, density)), size)
    }
