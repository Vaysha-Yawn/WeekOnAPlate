package week.on.a.plate.screens.menu.logic.useCase

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editPositionRecipeMore.event.ActionMoreRecipePositionEvent
import week.on.a.plate.dialogs.editPositionRecipeMore.logic.EditRecipePositionViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class RecipePositionActionsMore @Inject constructor(
    private val getSelAndUseCase: GetSelAndUseCase,
    private val changePortionsCountUseCase: ChangePortionsCountUseCase,
    private val addRecipeToCookPlanUseCase: AddRecipeToCookPlanUseCase,
    ) {

    suspend operator fun invoke(position: Position.PositionRecipeView, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit) {
        EditRecipePositionViewModel.launch(mainViewModel) { event ->
            mainViewModel.viewModelScope.launch {
                when (event) {
                    ActionMoreRecipePositionEvent.AddToCart -> onEvent(
                        MenuEvent.RecipeToShopList(
                            position
                        )
                    )

                    ActionMoreRecipePositionEvent.ChangePotionsCount -> changePortionsCountUseCase(position, mainViewModel, onEvent)
                    ActionMoreRecipePositionEvent.Delete -> onEvent(
                        MenuEvent.ActionDBMenu(
                            ActionWeekMenuDB.Delete(position)
                        )
                    )

                    ActionMoreRecipePositionEvent.Double -> getSelAndUseCase.getSelAndDouble(position, mainViewModel, onEvent)
                    ActionMoreRecipePositionEvent.FindReplace -> onEvent(
                        MenuEvent.FindReplaceRecipe(
                            position
                        )
                    )

                    ActionMoreRecipePositionEvent.Move -> getSelAndUseCase.getSelAndMove(position, mainViewModel, onEvent)
                    ActionMoreRecipePositionEvent.Close -> {}
                    ActionMoreRecipePositionEvent.CookPlan -> addRecipeToCookPlanUseCase(
                        position, mainViewModel
                    )
                }
            }
        }
    }
}