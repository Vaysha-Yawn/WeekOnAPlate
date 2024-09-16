package week.on.a.plate.core.navigation.bottomBar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import week.on.a.plate.ui.theme.ColorTextBlack
import week.on.a.plate.ui.theme.ColorTransparent


@Composable
fun BottomBar(navController: NavHostController, isActiveBaseScreen: Boolean) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (!isActiveBaseScreen) return
    BottomAppBar(
        Modifier.height(72.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        Row() {
            bottomScreens.forEach { topLevelRoute ->
                val sel = remember {
                    mutableStateOf(false)
                }
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route?.substringBefore("?") == topLevelRoute.route::class.qualifiedName } == true
                sel.value = isSelected
                val radius by animateFloatAsState(
                    if (isSelected) 10.0f else 0.0f,
                    label = "", animationSpec = tween(300)
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = topLevelRoute.icon),
                            contentDescription = "",
                            modifier = Modifier
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary else ColorTransparent,
                                    CircleShape
                                )
                                .padding(radius.dp),
                            tint = if (isSystemInDarkTheme() && isSelected) ColorTextBlack
                            else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(topLevelRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = ColorTransparent
                    )
                )
            }
        }
    }
}
