package week.on.a.plate.core.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.R

@Serializable
sealed class BottomScreens<T>(val icon: Int, val route: T) {
    @Serializable
    data object ShoppingList : BottomScreens<ShoppingListScreen>(
        icon = R.drawable.shopping_cart,
        route = ShoppingListScreen
    )

    @Serializable
    data object Menu : BottomScreens<MenuScreen>(icon = R.drawable.menu, route = MenuScreen)

    @Serializable
    data object Search : BottomScreens<SearchScreen>(icon = R.drawable.search, route = SearchScreen)

    @Serializable
    data object Settings :
        BottomScreens<SettingsScreen>(icon = R.drawable.settings, route = SettingsScreen)
}

val bottomScreens = listOf(
    BottomScreens.ShoppingList,
    BottomScreens.Menu,
    BottomScreens.Search,
    BottomScreens.Settings,
)

// destinations

@Serializable
object ShoppingListScreen

@Serializable
object MenuScreen

@Serializable
object SearchScreen

@Serializable
object SettingsScreen