package vip.cdms.weru

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import vip.cdms.weru.ui.AppNavigation
import vip.cdms.weru.ui.theme.AppTheme

val LocalWindowSizeClass: ProvidableCompositionLocal<WindowSizeClass> =
    if (RuntimePlatform is Platform.Desktop) compositionLocalOf { error("WindowSizeClass is not initialized") }
    else staticCompositionLocalOf { error("WindowSizeClass is not initialized") }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App() {
    AppTheme {
        CompositionLocalProvider(
            LocalWindowSizeClass provides calculateWindowSizeClass(),
        ) {
            AppNavigation()
        }
    }
}
