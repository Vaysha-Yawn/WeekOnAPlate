package week.on.a.plate.screens.menu.event

import week.on.a.plate.data.dataView.week.Position

sealed class SelectedEvent {
    class CheckRecipe(val recipe: Position.PositionRecipeView) : week.on.a.plate.screens.menu.event.SelectedEvent()
    class AddCheckState(val recipe: Position.PositionRecipeView) : week.on.a.plate.screens.menu.event.SelectedEvent()
}