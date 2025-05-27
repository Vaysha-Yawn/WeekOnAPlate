package week.on.a.plate.app.mainActivity.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import week.on.a.plate.core.navigation.BottomScreens
import week.on.a.plate.core.navigation.bottomScreens
import week.on.a.plate.core.theme.ColorTransparent
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.logic.SearchViewModel


@Composable
fun BottomBar(
    isActiveBaseScreen: Boolean,
    searchViewModel: SearchViewModel
) {
    val nav = rememberNavController()
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (!isActiveBaseScreen) return
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        Row() {
            bottomScreens.forEach { topLevelRoute ->
                val selected = rememberSaveable {
                    mutableStateOf(false)
                }
                val isSelected = currentDestination?.hierarchy?.any { it.route?.substringBefore("?") == topLevelRoute.route::class.qualifiedName } == true
                selected.value = isSelected
                val radius by animateFloatAsState(
                    if (isSelected) 10.0f else 0.0f,
                    label = "", animationSpec = tween(300)
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(id = topLevelRoute.icon),
                            contentDescription = topLevelRoute.route::class.qualifiedName,
                            modifier = Modifier
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary else ColorTransparent,
                                    CircleShape
                                )
                                .padding(radius.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        nav.navigate(topLevelRoute.route) {
                            if (topLevelRoute == BottomScreens.SearchBottomNav){
                                searchViewModel.onEvent(SearchScreenEvent.Clear)
                            }
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
