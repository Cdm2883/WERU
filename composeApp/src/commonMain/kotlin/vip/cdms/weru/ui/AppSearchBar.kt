package vip.cdms.weru.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vip.cdms.weru.LocalWindowSizeClass
import vip.cdms.weru.Platform
import vip.cdms.weru.RuntimePlatform
import vip.cdms.weru.ui.theme.isDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.AppSearchBar(modifier: Modifier, navigationWidth: Dp) {
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val startPadding by animateDpAsState(if (!expanded) navigationWidth + 16.dp else 0.dp, tween())
    val endPadding by animateDpAsState(if (!expanded) 16.dp else 0.dp, tween())
    val minWidth by animateDpAsState(
        if (LocalWindowSizeClass.current.widthSizeClass > WindowWidthSizeClass.Medium)
        /*SearchBarMaxWidth*/ 720.dp else /*SearchBarMinWidth*/ 360.dp
    )

    SearchBar(
        modifier = Modifier.align(Alignment.TopCenter).padding(start = startPadding, end = endPadding) then modifier,
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { query = it },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.sizeIn(minWidth = minWidth),
                enabled = true,
                placeholder = { Text("Search what u want...", maxLines = 1) },
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                trailingIcon = {
                    AnimatedContent(expanded) {
                        if (it) Icon(
                            Icons.Rounded.Close,
                            contentDescription = null,
                            Modifier.clip(MaterialTheme.shapes.extraSmall).clickable { query = ""; expanded = false }
                        ) else Icon(
                            Icons.Rounded.MoreVert,
                            contentDescription = null,
                            Modifier.clip(MaterialTheme.shapes.medium).clickable {
                                // TODO
                            }
                        )
                    }
                },
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        colors = SearchBarDefaults.colors(),
        shadowElevation = if (MaterialTheme.isDarkTheme) 8.dp else 0.dp,
        windowInsets = SearchBarDefaults.windowInsets.add(
            WindowInsets(top = if (RuntimePlatform is Platform.Desktop) 8.dp else 0.dp)
        ),
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            repeat(4) { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier =
                    Modifier.clickable {
                        query = resultText
                        expanded = false
                    }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}
