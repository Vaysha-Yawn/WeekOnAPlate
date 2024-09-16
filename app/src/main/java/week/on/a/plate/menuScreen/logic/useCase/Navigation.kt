package week.on.a.plate.menuScreen.logic.useCase

import androidx.navigation.NavHostController
import week.on.a.plate.core.navigation.destiations.FullScreenDialogRoute
import week.on.a.plate.menuScreen.data.eventData.NavFromMenuData
import javax.inject.Inject

class Navigation @Inject constructor() {

    lateinit var navController: NavHostController

    fun onEvent(data: NavFromMenuData) {
        when (data) {
            is NavFromMenuData.AddPositionToMenuDialog -> {
                navController.navigate(
                    FullScreenDialogRoute.AddPositionToMenuDialog(
                        data.position,
                        data.date,
                        data.category
                    )
                )
            }

            is NavFromMenuData.DoublePositionToMenu -> {
                navController.navigate(
                    FullScreenDialogRoute.DoublePositionToMenuDialog(
                        data.position,
                    )
                )
            }

            is NavFromMenuData.MovePositionToMenu -> {
                navController.navigate(
                    FullScreenDialogRoute.MovePositionToMenuDialog(
                        data.position,
                    )
                )
            }

            is NavFromMenuData.SpecifyDate -> {
                navController.navigate(
                    FullScreenDialogRoute.SpecifyDateDialog
                )
            }

            //
            is NavFromMenuData.FindReplaceRecipe -> TODO()
            is NavFromMenuData.NavToAddRecipe -> TODO()
            is NavFromMenuData.NavToChooseIngredient -> TODO()
            is NavFromMenuData.NavToCreateDraft -> TODO()
            is NavFromMenuData.NavToFullRecipe -> TODO()
            is NavFromMenuData.SearchByDraft -> TODO()

        }
    }
}