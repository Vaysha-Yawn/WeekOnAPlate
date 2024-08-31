package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import week.on.a.plate.core.data.week.RecipeInMenuView


class SelectionMapper() {
    fun SelectionRoom.roomToView(recipes: MutableList<RecipeInMenuView>): week.on.a.plate.core.data.week.SelectionView =
        week.on.a.plate.core.data.week.SelectionView(
            id = this.selectionId,
            category = this.category,
            recipes = recipes
        )

    fun week.on.a.plate.core.data.week.SelectionView.viewToRoom(dayId:Long): SelectionRoom =
        SelectionRoom(
            dayId = dayId,
            category = this.category
        )
}
