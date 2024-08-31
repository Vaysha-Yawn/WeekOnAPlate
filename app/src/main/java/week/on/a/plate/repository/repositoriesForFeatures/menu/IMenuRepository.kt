package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.WeekView
import java.time.LocalDate

interface IMenuRepository {
    suspend fun addRecipeInMenu()
    suspend fun changeRecipeInMenuState(newState:RecipeStateView)
    suspend fun deleteRecipeInMenu()
    suspend fun changeRecipeInRecipeInMenu(newRecipe:RecipeShortView)
    suspend fun changePortionsCount(newCount:Int)
    suspend fun insertNewWeek(week: WeekView)
    suspend fun getCurrentWeek(day:LocalDate): Flow<WeekView>
}