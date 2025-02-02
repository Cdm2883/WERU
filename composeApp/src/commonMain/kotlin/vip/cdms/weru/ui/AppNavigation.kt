package vip.cdms.weru.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.material3.SearchBarDefaults.InputFieldHeight
import androidx.compose.material3.adaptive.navigationsuite.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import vip.cdms.weru.LocalWindowSizeClass
import vip.cdms.weru.ui.components.RelationGraph
import vip.cdms.weru.ui.icons.*
import vip.cdms.weru.ui.utils.drawInnerRightRoundCorner
import vip.cdms.weru.ui.utils.drawInnerTopRoundCorner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    Box {
        var currentNavigationBarDestination by rememberSaveable { mutableStateOf(AppNavigationBarDestinations.HOME) }
        var navigationWidth by rememberSaveable { mutableStateOf(0) }

        val searchBarVisible = currentNavigationBarDestination == AppNavigationBarDestinations.HOME
        val searchBarOffsetY by animateDpAsState(
            if (!searchBarVisible) -InputFieldHeight - 32.dp else 0.dp,
            tween(easing = CubicBezierEasing(0f, 1f, 1f, 1f))
        )
        AppSearchBar(
            modifier = Modifier.offset(y = searchBarOffsetY),
            navigationWidth = with(LocalDensity.current) { navigationWidth.toDp() },
        )

        AppNavigationSuiteScaffold(
            currentNavigationBarDestination,
            onWidth = { navigationWidth = it },
            onSwitchTo = {
                currentNavigationBarDestination = it
            }
        ) {
            Row(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxHeight().fillMaxWidth(1f / 1)) {
                    RelationGraph()
                    Spacer(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(16.dp)
                            .background(Color.Blue)
                            .padding(16.dp)
                            .background(Color.Green)
                            .padding(16.dp)
                            .background(Color.Yellow)
                            .padding(16.dp)
                            .background(Color.Magenta)
                            .padding(16.dp)
                            .background(Color.Cyan)
                            .padding(16.dp)
                            .background(Color.Gray)
                            .padding(16.dp)
                            .background(Color.Black)
                            .padding(16.dp)
                            .background(Color.White)
                            .padding(16.dp)
                            .background(Color.LightGray)
                            .padding(16.dp)
                            .background(Color.DarkGray)
                    )
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button({}) {
                            Text("Test Navigation")
                        }
                    }
                }
                // panel 2
            }
        }
    }
}

enum class AppNavigationBarDestinations(
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
) {
    HOME("Home", Icons.Rounded.HomeOutline, Icons.Rounded.Home),
    PLUGINS("Plugins", Icons.Rounded.ExtensionOutline, Icons.Rounded.Extension),
    SETTING("Settings", Icons.Rounded.SettingsOutline, Icons.Rounded.Settings),
}

@Composable
fun AppNavigationSuiteScaffold(
    currentNavigationBarDestination: AppNavigationBarDestinations,
    onWidth: (Int) -> Unit,
    onSwitchTo: (AppNavigationBarDestinations) -> Unit,
    content: @Composable () -> Unit = {}
) {
    val windowWidthSizeClass = LocalWindowSizeClass.current.widthSizeClass
    val isNavigationBar = windowWidthSizeClass <= WindowWidthSizeClass.Compact
    val layoutType =
        if (windowWidthSizeClass >= WindowWidthSizeClass.Medium) NavigationSuiteType.NavigationRail
        else NavigationSuiteType.NavigationBar
    val innerCorner = MaterialTheme.shapes.medium
    NavigationSuiteScaffoldLayout(
        navigationSuite = {
            val containerColor = NavigationBarDefaults.containerColor
            val density = LocalDensity.current
            NavigationSuite(
                modifier = Modifier
                    .drawBehind {
                        if (isNavigationBar) drawInnerTopRoundCorner(containerColor, innerCorner, density)
                        else drawInnerRightRoundCorner(containerColor, innerCorner, density)
                    }
                    .run { if (!isNavigationBar) padding(top = 8.dp) else this }
                    .onGloballyPositioned { onWidth(if (!isNavigationBar) it.size.width/* + it.positionInParent().x */ else 0) },
                layoutType = layoutType,
                colors = NavigationSuiteDefaults.colors(
                    navigationRailContainerColor = containerColor,
                )
            ) {
                AppNavigationBarDestinations.entries.forEach {
                    val selected = it == currentNavigationBarDestination
                    item(
                        selected = selected,
                        onClick = {
                            onSwitchTo(it)
                        },
                        icon = {
                            if (it != AppNavigationBarDestinations.SETTING) {
                                Icon(it.icon, contentDescription = it.label)
                                val transformOrigin = TransformOrigin(.5f, .8f)
                                AnimatedVisibility(
                                    selected,
                                    enter = fadeIn() + scaleIn(transformOrigin = transformOrigin),
                                    exit = fadeOut() + scaleOut(transformOrigin = transformOrigin),
                                ) {
                                    Icon(it.iconSelected, contentDescription = it.label)
                                }
                            } else AnimatedContent(
                                selected,
                                transitionSpec = { fadeIn() togetherWith fadeOut() },
                            ) { visible ->
                                if (visible) Icon(it.iconSelected, contentDescription = it.label)
                                else Icon(it.icon, contentDescription = it.label)
                            }
                        },
//                        modifier = Modifier,
//                        enabled = true,
                        label = { Text(it.label) },
//                        alwaysShowLabel = false,
//                        badge = {},
                    )
                }
            }
        },
        layoutType = layoutType,
        content = {
            Box(
                Modifier.consumeWindowInsets(
                    when (layoutType) {
                        NavigationSuiteType.NavigationBar ->
                            NavigationBarDefaults.windowInsets
                        NavigationSuiteType.NavigationRail ->
                            NavigationRailDefaults.windowInsets
                        else -> WindowInsets(0, 0, 0, 0)
                    }
                ).run {
                    if (!isNavigationBar) clip(
                        innerCorner.copy(
                            topEnd = ZeroCornerSize,
                            bottomEnd = ZeroCornerSize
                        )  // yes, duplicate with drawBehind as same behavior,
                        // but I don't want to waste my code I have been writing for a long time qwq
                        // and just only do this could support ltf & rtl.
                    ).background(MaterialTheme.colorScheme.surface) else this
                }
            ) {
                content()
            }
        }
    )
}
