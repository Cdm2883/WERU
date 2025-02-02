package vip.cdms.weru.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalContextMenuRepresentation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.LocalTextContextMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.dzirbel.contextmenu.ContextMenuColors
import com.dzirbel.contextmenu.MaterialContextMenuRepresentation
import com.dzirbel.contextmenu.MaterialTextContextMenu
import com.materialkolor.ktx.themeColorOrNull
import com.materialkolor.rememberDynamicColorScheme
import com.mayakapps.compose.windowstyler.WindowBackdrop
import com.mayakapps.compose.windowstyler.WindowCornerPreference
import com.mayakapps.compose.windowstyler.WindowFrameStyle
import com.mayakapps.compose.windowstyler.WindowStyle
import vip.cdms.weru.LocalWindowScope
import java.io.File
import javax.imageio.ImageIO

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    contrast: Contrast,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val (defaultColorScheme, extendedColorScheme) = getDefaultColorSchemes(darkTheme, contrast)

    val colorScheme = if (!dynamicColor || contrast != Contrast.Normal) defaultColorScheme else {
        getWallpaper()
            ?.toComposeImageBitmap()
            ?.themeColorOrNull()
            ?.let { rememberDynamicColorScheme(it, isDark = darkTheme, isAmoled = false) }
            ?: defaultColorScheme
    }

    LocalWindowScope.current.WindowStyle(
        isDarkTheme = darkTheme,
        backdropType = WindowBackdrop.Solid(colorScheme.primary),
        frameStyle = WindowFrameStyle(
            borderColor = colorScheme.primary,
            titleBarColor = colorScheme.primary,
            captionColor = colorScheme.onPrimary,
            cornerPreference = WindowCornerPreference.ROUNDED,
        ),
    )

    AppTheme0(
        darkTheme,
        colorScheme,
        extendedColorScheme
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.small)
                .background(colorScheme.surface)
        ) {
            CompositionLocalProvider(
                LocalContextMenuRepresentation provides MaterialContextMenuRepresentation(
                    colors = ContextMenuColors(
                        surface = MenuDefaults.containerColor,
                        text = MenuDefaults.itemColors().textColor,
                        shortcutText = MenuDefaults.itemColors().trailingIconColor,
                        icon = MenuDefaults.itemColors().leadingIconColor,
                        divider = DividerDefaults.color,
                    )
                ),
                LocalTextContextMenu provides MaterialTextContextMenu,
                content = content
            )
        }
    }
}

fun getWallpaper() = runCatching {  // holy shit, lines too long
    val os = System.getProperty("os.name").lowercase()
    fun exec(vararg commands: String) =
        ProcessBuilder(*commands).start().inputStream.bufferedReader().use { it.readLine() }
    val wallpaper = when {
        "win" in os -> System.getProperty("user.home") + "/AppData/Roaming/Microsoft/Windows/Themes/TranscodedWallpaper"
        "mac" in os -> exec("osascript", "-e", "tell application \"Finder\" to get POSIX path of (get desktop picture as alias)")
        "nix" in os || "nux" in os || "aix" in os -> when (System.getenv("DESKTOP_SESSION")) {
            "gnome", "unity" -> exec("bash", "-c", "gsettings get org.gnome.desktop.background picture-uri")
            "kde" -> exec("bash", "-c", "qdbus org.kde.plasmashell /PlasmaShell org.kde.PlasmaShell.evaluateScript \"var a = desktops(); print(a[0].wallpaper());\"")
            "xfce" -> exec("bash", "-c", "xfconf-query -c xfce4-desktop -p /backdrop/screen0/monitor0/image-path")
            else -> null
        }?.replace("'", "")?.replace("file://", "")
        else -> null
    }?.takeUnless { it.isBlank() } ?: return@runCatching null
    ImageIO.read(File(wallpaper))
}.getOrNull()
