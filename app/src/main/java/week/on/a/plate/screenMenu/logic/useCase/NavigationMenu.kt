package week.on.a.plate.screenMenu.logic.useCase

import androidx.navigation.NavHostController
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelection
import week.on.a.plate.screenMenu.event.NavFromMenuData
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import javax.inject.Inject

class NavigationMenu @Inject constructor() {

    lateinit var navController: NavHostController

    fun onEvent(data: NavFromMenuData) {
        when (data) {
            is NavFromMenuData.SpecifySelection -> { navController.navigate(SpecifySelection) }
            is NavFromMenuData.NavToChooseIngredient -> TODO()
            is NavFromMenuData.NavToCreateDraft -> TODO()
            is NavFromMenuData.NavToFullRecipe -> {
                navController.navigate(RecipeDetailsDestination)
            }
        }
    }
}