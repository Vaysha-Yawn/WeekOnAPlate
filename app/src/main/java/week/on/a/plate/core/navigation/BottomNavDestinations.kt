package week.on.a.plate.core.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.utils.DateTypeConverter
import java.time.LocalDate
import kotlin.jvm.java

@Serializable
sealed class BottomScreens<T>(val icon: Int, val route: T) {
    @Serializable
    data object ShoppingListBottomNav : BottomScreens<ShoppingListDestination>(
        icon = R.drawable.shopping_cart,
        route = ShoppingListDestination
    )

    @Serializable
    data object MenuBottomNav :
        BottomScreens<MenuDestination>(icon = R.drawable.menu, route = MenuDestination())

    @Serializable
    data object SearchBottomNav :
        BottomScreens<SearchDestination>(icon = R.drawable.search, route = SearchDestination())

    @Serializable
    data object SettingsBottomNav :
        BottomScreens<SettingsDestination>(icon = R.drawable.settings, route = SettingsDestination)

    @Serializable
    data object CookPlannerBottomNav :
        BottomScreens<CookPlannerDestination>(
            icon = R.drawable.cook_cap,
            route = CookPlannerDestination
        )
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

@Serializable(LocalDateSerializer::class)
data class MenuDestination(val dateLaunch: LocalDate? = null)

@Serializable
data class SearchDestination(
    val selId: Long? = null,
    val filters: Pair<List<RecipeTagView>, List<IngredientView>>? = null
)

@Serializable
object SettingsDestination

@Serializable
object CookPlannerDestination