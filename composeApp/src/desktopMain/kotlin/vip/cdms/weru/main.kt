package vip.cdms.weru

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.*
import org.jetbrains.compose.resources.painterResource
import weru.composeapp.generated.resources.Res
import weru.composeapp.generated.resources.compose_multiplatform

val LocalWindowState = staticCompositionLocalOf<WindowState> { error("WindowState is not initialized") }
val LocalWindowScope = staticCompositionLocalOf<WindowScope> { error("WindowScope is not initialized") }

fun main() = application {
    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "WERU Desktop",
        icon = painterResource(Res.drawable.compose_multiplatform),
    ) {
        CompositionLocalProvider(
            LocalWindowState provides windowState,
            LocalWindowScope provides this,
        ) {
            App()
        }
    }
}
