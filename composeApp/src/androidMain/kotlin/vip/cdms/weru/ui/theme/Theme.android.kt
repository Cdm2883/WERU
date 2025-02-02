package vip.cdms.weru.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    contrast: Contrast,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val (defaultColorScheme, extendedColorScheme) = getDefaultColorSchemes(darkTheme, contrast)
    val colorScheme = when {
        // Dynamic color is available on Android 12+
        contrast == Contrast.Normal && dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> defaultColorScheme
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(colorScheme.surface)
    ) {
        AppTheme0(
            darkTheme,
            colorScheme,
            extendedColorScheme,
            content
        )
    }
}
