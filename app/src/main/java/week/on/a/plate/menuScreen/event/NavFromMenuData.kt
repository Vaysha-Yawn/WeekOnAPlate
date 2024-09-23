package week.on.a.plate.menuScreen.event

import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView

sealed class NavFromMenuData {
    class NavToFullRecipe(val rec: RecipeShortView) : NavFromMenuData()
    class SearchByDraft(val draft: Position.PositionDraftView) : NavFromMenuData()
    class FindReplaceRecipe(val recipe: Position.PositionRecipeView) : NavFromMenuData()
    class NavToAddRecipe(val selId: Long?) : NavFromMenuData()
    data object SpecifySelection : NavFromMenuData()
    data object NavToChooseIngredient : NavFromMenuData()
    data object NavToCreateDraft : NavFromMenuData()
}