package week.on.a.plate.screenMenu.event

import week.on.a.plate.data.dataView.week.Position

sealed class SelectedEvent {
    class CheckRecipe(val recipe: Position.PositionRecipeView) : SelectedEvent()
    class AddCheckState(val recipe: Position.PositionRecipeView) : SelectedEvent()
}