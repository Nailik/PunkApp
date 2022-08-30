package com.example.punkapp.ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

val Science: ImageVector
    get() {
        if (_science != null) {
            return _science!!
        }
        _science = materialIcon(name = "Filled.Science") {
            materialPath {
                moveTo(13.0f, 11.33f)
                lineTo(18.0f, 18.0f)
                horizontalLineTo(6.0f)
                lineToRelative(5.0f, -6.67f)
                verticalLineTo(6.0f)
                horizontalLineToRelative(2.0f)
                moveTo(15.96f, 4.0f)
                horizontalLineTo(8.04f)
                curveTo(7.62f, 4.0f, 7.39f, 4.48f, 7.65f, 4.81f)
                lineTo(9.0f, 6.5f)
                verticalLineToRelative(4.17f)
                lineTo(3.2f, 18.4f)
                curveTo(2.71f, 19.06f, 3.18f, 20.0f, 4.0f, 20.0f)
                horizontalLineToRelative(16.0f)
                curveToRelative(0.82f, 0.0f, 1.29f, -0.94f, 0.8f, -1.6f)
                lineTo(15.0f, 10.67f)
                verticalLineTo(6.5f)
                lineToRelative(1.35f, -1.69f)
                curveTo(16.61f, 4.48f, 16.38f, 4.0f, 15.96f, 4.0f)
                lineTo(15.96f, 4.0f)
                close()
            }
        }
        return _science!!
    }

private var _science: ImageVector? = null
