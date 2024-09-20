package week.on.a.plate.menuScreen.logic.useCase

import androidx.navigation.NavHostController
import week.on.a.plate.SpecifySelection.navigation.SpecifySelection
import week.on.a.plate.menuScreen.data.eventData.NavFromMenuData
import javax.inject.Inject

class NavigationMenu @Inject constructor() {

    lateinit var navController: NavHostController

    fun onEvent(data: NavFromMenuData) {
        when (data) {
            is NavFromMenuData.SpecifySelection -> { navController.navigate(SpecifySelection) }
            is NavFromMenuData.FindReplaceRecipe -> TODO()
            is NavFromMenuData.NavToAddRecipe -> TODO()
            is NavFromMenuData.NavToChooseIngredient -> TODO()
            is NavFromMenuData.NavToCreateDraft -> TODO()
            is NavFromMenuData.NavToFullRecipe -> TODO()
            is NavFromMenuData.SearchByDraft -> TODO()

        }
    }
}