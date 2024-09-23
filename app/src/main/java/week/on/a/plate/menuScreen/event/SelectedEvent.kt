package week.on.a.plate.menuScreen.event

import week.on.a.plate.core.data.week.Position

sealed class SelectedEvent {
    class CheckRecipe(val recipe: Position.PositionRecipeView) : SelectedEvent()
    class AddCheckState(val recipe: Position.PositionRecipeView) : SelectedEvent()
    data object ChooseAll : SelectedEvent()
}




