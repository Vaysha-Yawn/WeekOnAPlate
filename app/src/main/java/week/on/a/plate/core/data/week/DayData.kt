package week.on.a.plate.core.data.week

import java.util.Date

data class DayData(
    val id: Long,
    val date: Date,
    val dayInWeek: DayInWeekData,
    val selections: MutableList<SelectionInDayData>
){
    fun findRecipe(id:Long): RecipeInMenu?{
        return selections.find { sel->sel.recipes.find { res-> res.id == id}!=null }?.recipes?.find { res-> res.id == id}
    }
}