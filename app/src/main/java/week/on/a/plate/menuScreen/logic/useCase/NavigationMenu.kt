package week.on.a.plate.menuScreen.logic.useCase

import androidx.navigation.NavHostController
import week.on.a.plate.core.fullScereenDialog.specifySelection.navigation.SpecifySelection
import week.on.a.plate.menuScreen.event.NavFromMenuData
import javax.inject.Inject

class NavigationMenu @Inject constructor() {

    lateinit var navController: NavHostController

    fun onEvent(data: NavFromMenuData) {
        when (data) {
            is NavFromMenuData.SpecifySelection -> { navController.navigate(SpecifySelection) }
            is NavFromMenuData.NavToChooseIngredient -> TODO()
            is NavFromMenuData.NavToCreateDraft -> TODO()
            is NavFromMenuData.NavToFullRecipe -> TODO()
        }
    }
}