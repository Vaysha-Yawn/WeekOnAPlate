package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.RecipeInMenu
import week.on.a.plate.repository.repositoriesForFeatures.MenuRepository
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR:MenuRepository
) {
    // доступ к бд
    var weekDataExample = WeekDataExample
    fun actionAddRecipeToCategory(date: Int, category: String, recipe: RecipeInMenu) {
        //+case date == 0 sel = "нераспределенное" = week нераспред
    }

    fun actionRecipeToNextStep(id:Long){

    }

    fun actionDeleteSelected(value: List<Long>) {

    }

}