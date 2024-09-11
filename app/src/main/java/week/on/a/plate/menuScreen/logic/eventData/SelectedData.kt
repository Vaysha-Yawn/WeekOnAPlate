package week.on.a.plate.menuScreen.logic.eventData

import week.on.a.plate.core.data.week.Position

sealed class SelectedData {
    class CheckRecipe(val recipe: Position.PositionRecipeView) : SelectedData()
    class AddCheckState(val recipe: Position.PositionRecipeView) : SelectedData()
    data object ChooseAll : SelectedData()
}




