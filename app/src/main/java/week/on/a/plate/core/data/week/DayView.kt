package week.on.a.plate.core.data.week

import java.time.LocalDate

data class DayView(
    val id: Long,
    val date: LocalDate,
    val dayInWeek: DayInWeekData,
    val selections: MutableList<SelectionView>
){
    fun findRecipe(id:Long): RecipeInMenuView?{
        return selections.find { sel->sel.recipes.find { res-> res.id == id}!=null }?.recipes?.find { res-> res.id == id}
    }
}