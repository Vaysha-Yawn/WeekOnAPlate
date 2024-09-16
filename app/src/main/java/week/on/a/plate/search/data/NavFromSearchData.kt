package week.on.a.plate.search.data

import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import java.time.LocalDate

sealed class NavFromSearchData {
    class NavToFullRecipe(val rec: RecipeShortView) : NavFromSearchData()

}