package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import week.on.a.plate.core.data.week.RecipeInMenu


class SelectionInDayMapper() {
    fun SelectionInDay.roomToView(recipes: MutableList<RecipeInMenu>): week.on.a.plate.core.data.week.SelectionInDayData =
        week.on.a.plate.core.data.week.SelectionInDayData(
            id = this.selectionId,
            category = this.category,
            recipes = recipes
        )

    fun week.on.a.plate.core.data.week.SelectionInDayData.viewToRoom(dayId:Long): SelectionInDay =
        SelectionInDay(
            selectionId = this.id,
            dayId = dayId,
            category = this.category
        )
}
