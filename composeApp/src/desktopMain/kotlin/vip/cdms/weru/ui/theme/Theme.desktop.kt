package vip.cdms.weru.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.materialkolor.ktx.themeColorOrNull
import com.materialkolor.rememberDynamicColorScheme
import java.io.File
import javax.imageio.ImageIO

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    contrast: Contrast,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    val (defaultColorScheme, extendedColorScheme) = getDefaultColorSchemes(darkTheme, contrast)

    val colorScheme = if (contrast != Contrast.Normal) defaultColorScheme else {
        getWallpaper()
            ?.toComposeImageBitmap()
            ?.themeColorOrNull()
            ?.let { rememberDynamicColorScheme(it, isDark = darkTheme, isAmoled = false) }
            ?: defaultColorScheme
    }

    AppTheme0(
        colorScheme,
        extendedColorScheme,
        content
    )
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
