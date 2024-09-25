package week.on.a.plate.screenMenu.event

import week.on.a.plate.data.dataView.week.RecipeShortView

sealed class NavFromMenuData {
    class NavToFullRecipe(val rec: RecipeShortView) : NavFromMenuData()
    data object SpecifySelection : NavFromMenuData()
    data object NavToChooseIngredient : NavFromMenuData()
    data object NavToCreateDraft : NavFromMenuData()
}