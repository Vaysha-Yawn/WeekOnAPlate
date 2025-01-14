package week.on.a.plate.screens.menu.logic.useCase

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOtherPositionMore.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.editOtherPositionMore.logic.EditOtherPositionViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class OtherPositionActionsMore @Inject constructor(
    private val addIngredientToShoppingListInBd: AddIngredientToShoppingListInBd,
    private val getSelAndUseCase: GetSelAndUseCase
) {

    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit,
        onEventMain: (MainEvent) -> Unit,
    ) {
        EditOtherPositionViewModel.launch(
            position is Position.PositionIngredientView,
            mainViewModel
        ) { event ->
            when (event) {
                OtherPositionMoreEvent.Close -> {}
                OtherPositionMoreEvent.Delete -> onEvent(
                    MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.Delete(
                            position
                        )
                    )
                )

                OtherPositionMoreEvent.Double -> getSelAndUseCase.getSelAndDouble(position, mainViewModel, onEvent)

                OtherPositionMoreEvent.Edit -> onEvent(MenuEvent.EditOtherPosition(position))

                OtherPositionMoreEvent.Move -> getSelAndUseCase.getSelAndMove(position, mainViewModel, onEvent)

                is OtherPositionMoreEvent.AddToShopList -> {
                    mainViewModel.viewModelScope.launch {
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