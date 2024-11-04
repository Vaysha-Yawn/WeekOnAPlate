package week.on.a.plate.screens.tutorial.state

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.screens.filters.navigation.FilterDestination

data class TutorialStateUI(
    var targetDestination: TutorialDestination,
    val activePageInd: MutableIntState,
    val listPages: List<TutorialPageStateUI>
)

data class TutorialPageStateUI(
    val img: Int,
    val title: String,
    val description: String
)

enum class TutorialDestination(target: Any) {
    Menu(MenuDestination),
    ShoppingList(ShoppingListDestination),
    CookPlanner(CookPlannerDestination),
    Filter(FilterDestination),
    Search(SearchDestination)
}