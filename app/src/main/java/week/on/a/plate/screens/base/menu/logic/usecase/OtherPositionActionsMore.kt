package week.on.a.plate.screens.base.menu.logic.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.forMenuScreen.editOtherPositionMore.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.forMenuScreen.editOtherPositionMore.logic.EditOtherPositionViewModel
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndUseCase
import javax.inject.Inject

//todo slice
class OtherPositionActionsMore @Inject constructor(
    private val addIngredientToShoppingListInBd: AddIngredientToShoppingListInBd,
    private val getSelAndUseCase: GetSelAndUseCase,

    ) {
    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
        onEventMenu: (MenuEvent) -> Unit,
        onEventMain: (MainEvent) -> Unit,
    ) = coroutineScope {
        EditOtherPositionViewModel.launch(
            position is Position.PositionIngredientView,
            mainViewModel
        ) { event ->
            when (event) {
                OtherPositionMoreEvent.Close -> {}
                OtherPositionMoreEvent.Delete -> onEventMenu(
                    MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.Delete(
                            position
                        )
                    )
                )

                OtherPositionMoreEvent.Double -> getSelAndUseCase.getSelAndDouble(
                    position,
                    mainViewModel,
                    onEventMenu
                )

                OtherPositionMoreEvent.Edit -> onEventMenu(MenuEvent.EditOtherPosition(position))
                OtherPositionMoreEvent.Move -> getSelAndUseCase.getSelAndMove(
                    position,
                    mainViewModel,
                    onEventMenu
                )

                is OtherPositionMoreEvent.AddToShopList -> {
                    launch {
                        if (position is Position.PositionIngredientView) {
                            val ingredientNew = IngredientInRecipeView(
                                0,
                                position.ingredient.ingredientView,
                                position.ingredient.description,
                                position.ingredient.count
                            )
                            addIngredientToShoppingListInBd(
                                ingredientNew,
                                event.contextProvider,
                                onEventMain
                            )
                        }
                    }
                }
            }
        }
    }
}