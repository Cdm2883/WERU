package vip.cdms.weru.ui.theme

import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    contrast: Contrast,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val (defaultColorScheme, extendedColorScheme) = getDefaultColorSchemes(darkTheme, contrast)

    // TODO: support dynamic color scheme for desktop

    AppTheme0(
        defaultColorScheme,
        extendedColorScheme,
        content
    )
}
