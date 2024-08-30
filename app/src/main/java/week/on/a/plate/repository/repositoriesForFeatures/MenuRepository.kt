package week.on.a.plate.repository.repositoriesForFeatures

import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRepository
import week.on.a.plate.repository.tables.weekOrg.day.DayDataRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDayRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    val recipeInMenuRepository: RecipeInMenuRepository,
    val weekRepository: WeekDataRepository,
    val dayRepository: DayDataRepository,
    val selectionRepository: SelectionInDayRepository
) {

    /*fun getWeek():Flow<List<WeekData>>{

    }*/

    /*fun read(): Flow<List<Ingredient>> = dao.getAll()

    suspend fun create(ingredient: Ingredient) {
        dao.insert(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        dao.update(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        dao.delete(ingredient)
    }*/

}




