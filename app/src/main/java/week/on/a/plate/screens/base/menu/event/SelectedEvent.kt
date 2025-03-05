package week.on.a.plate.screens.base.menu.event

import week.on.a.plate.data.dataView.week.Position

sealed class SelectedEvent {
    class CheckRecipe(val recipe: Position.PositionRecipeView) : SelectedEvent()
    class AddCheckState(val recipe: Position.PositionRecipeView) : SelectedEvent()
}