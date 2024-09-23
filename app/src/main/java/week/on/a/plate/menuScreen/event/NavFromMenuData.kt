package week.on.a.plate.menuScreen.event

import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView

sealed class NavFromMenuData {
    class NavToFullRecipe(val rec: RecipeShortView) : NavFromMenuData()
    data object SpecifySelection : NavFromMenuData()
    data object NavToChooseIngredient : NavFromMenuData()
    data object NavToCreateDraft : NavFromMenuData()
}