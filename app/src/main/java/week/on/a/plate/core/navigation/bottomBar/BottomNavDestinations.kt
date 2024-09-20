package week.on.a.plate.core.navigation.bottomBar

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
    data object Home : BottomScreens<HomeScreen>(icon = R.drawable.home, route = HomeScreen)

    @Serializable
    data object Favorites :
        BottomScreens<FavoritesScreen>(icon = R.drawable.bookmark, route = FavoritesScreen)

    @Serializable
    data object Settings :
        BottomScreens<SettingsScreen>(icon = R.drawable.settings, route = SettingsScreen)
}

val bottomScreens = listOf(
    BottomScreens.ShoppingList,
    BottomScreens.Menu,
    BottomScreens.Home,
    BottomScreens.Favorites,
    BottomScreens.Settings,
)

// destinations

@Serializable
object ShoppingListScreen

@Serializable
object MenuScreen

@Serializable
object HomeScreen

@Serializable
object FavoritesScreen

@Serializable
object SettingsScreen