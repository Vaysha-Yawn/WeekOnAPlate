package week.on.a.plate.screens.additional.tutorial.state

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination

class TutorialStateUI(){
    val activePageInd: MutableIntState = mutableIntStateOf(0)
    val sizePages: MutableIntState = mutableIntStateOf(5)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val imgUri = mutableStateOf("")
}

data class TutorialPage(
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