package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.event.ActionMoreRecipePositionEvent
import week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.logic.EditRecipePositionViewModel
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.AddRecipeToCookPlanUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.ChangePortionsCountUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndUseCase
import javax.inject.Inject

//todo slice
class RecipePositionActionsMore @Inject constructor(
    private val getSelAndUseCase: GetSelAndUseCase,
    private val changePortionsCountUseCase: ChangePortionsCountUseCase,
    private val addRecipeToCookPlanUseCase: AddRecipeToCookPlanUseCase,
    private val deleteRecipe: DeleteRecipePosInDBUseCase
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) = coroutineScope {
        EditRecipePositionViewModel.launch(mainViewModel) { event ->
            launch {
                when (event) {
                    ActionMoreRecipePositionEvent.AddToCart -> onEvent(
                        MenuEvent.RecipeToShopList(
                            position
                        )
                    )

                    ActionMoreRecipePositionEvent.ChangePotionsCount -> changePortionsCountUseCase(
                        position,
                        mainViewModel
                    )

                    ActionMoreRecipePositionEvent.Delete ->
                        launch(Dispatchers.IO) {
                            deleteRecipe(position)
                        }

                    ActionMoreRecipePositionEvent.Double -> getSelAndUseCase.getSelAndDouble(
                        position,
                        mainViewModel,
                        onEvent
                    )

                    ActionMoreRecipePositionEvent.FindReplace -> onEvent(
                        MenuEvent.FindReplaceRecipe(
                            position
                        )
                    )

                    ActionMoreRecipePositionEvent.Move -> getSelAndUseCase.getSelAndMove(
                        position,
                        mainViewModel,
                        onEvent
                    )

                    ActionMoreRecipePositionEvent.Close -> {}
                    ActionMoreRecipePositionEvent.CookPlan -> addRecipeToCookPlanUseCase(
                        position, mainViewModel
                    )
                }
            }
        }
    }
}