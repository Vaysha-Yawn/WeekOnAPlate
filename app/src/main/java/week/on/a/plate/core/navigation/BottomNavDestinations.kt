package week.on.a.plate.core.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.R

@Serializable
sealed class BottomScreens<T>(val icon: Int, val route: T) {
    @Serializable
    data object ShoppingListBottomNav : BottomScreens<ShoppingListDestination>(
        icon = R.drawable.shopping_cart,
        route = ShoppingListDestination
    )

    @Serializable
    data object MenuBottomNav : BottomScreens<MenuDestination>(icon = R.drawable.menu, route = MenuDestination)

    @Serializable
    data object SearchBottomNav : BottomScreens<SearchDestination>(icon = R.drawable.search, route = SearchDestination)

    @Serializable
    data object SettingsBottomNav :
        BottomScreens<SettingsDestination>(icon = R.drawable.settings, route = SettingsDestination)

    @Serializable
    data object CookPlannerBottomNav :
        BottomScreens<CookPlannerDestination>(icon = R.drawable.cook_cap, route = CookPlannerDestination)
}

val bottomScreens = listOf(
    BottomScreens.ShoppingListBottomNav,
    BottomScreens.CookPlannerBottomNav,
    BottomScreens.MenuBottomNav,
    BottomScreens.SearchBottomNav,
    BottomScreens.SettingsBottomNav,
)

// destinations

@Serializable
object ShoppingListDestination

@Serializable
object MenuDestination

@Serializable
object SearchDestination

@Serializable
object SettingsDestination

@Serializable
object CookPlannerDestination