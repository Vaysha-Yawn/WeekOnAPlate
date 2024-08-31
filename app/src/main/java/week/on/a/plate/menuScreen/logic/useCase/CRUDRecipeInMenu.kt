package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.repository.repositoriesForFeatures.menu.MenuRepository
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: MenuRepository
) {

    fun actionAddRecipeToCategory(date: Int, category: String, recipe: RecipeInMenuView) {
        //+case date == 0 sel = "нераспределенное" = week нераспред
    }

    fun actionRecipeToNextStep(id:Long){

    }

    fun actionDeleteSelected(value: List<Long>) {

    }

}