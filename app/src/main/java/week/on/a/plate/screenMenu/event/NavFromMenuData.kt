package week.on.a.plate.screenMenu.event

import week.on.a.plate.data.dataView.week.RecipeShortView

sealed class NavFromMenuData {
    class NavToFullRecipe(val recId: Long, val portionsCount:Int ) : NavFromMenuData()
    data object SpecifySelection : NavFromMenuData()
}