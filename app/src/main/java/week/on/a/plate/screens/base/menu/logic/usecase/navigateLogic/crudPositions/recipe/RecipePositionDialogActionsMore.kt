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
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndDoubleUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndMoveUseCase
import javax.inject.Inject


class RecipePositionDialogActionsMore @Inject constructor(
    private val getSelAndMove: GetSelAndMoveUseCase,
    private val getSelAndDouble: GetSelAndDoubleUseCase,
    private val changePortionsRecipePosOpenDialog: ChangePortionsRecipePosOpenDialog,
    private val navToAddRecipeToCookPlan: NavToAddRecipeToCookPlan,
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

                    ActionMoreRecipePositionEvent.ChangePotionsCount -> changePortionsRecipePosOpenDialog(
                        position,
                        mainViewModel
                    )

                    ActionMoreRecipePositionEvent.Delete ->
                        launch(Dispatchers.IO) {
                            deleteRecipe(position)
                        }

                    ActionMoreRecipePositionEvent.Double -> getSelAndDouble(
                        position,
                        mainViewModel
                    )

                    is ActionMoreRecipePositionEvent.FindReplace -> onEvent(
                        MenuEvent.FindReplaceRecipe(
                            position, event.context
                        )
                    )

                    ActionMoreRecipePositionEvent.Move -> getSelAndMove(
                        position,
                        mainViewModel
                    )

                    ActionMoreRecipePositionEvent.CookPlan -> navToAddRecipeToCookPlan(
                        position, mainViewModel
                    )

                    ActionMoreRecipePositionEvent.Close -> {}
                }
            }
        }
    }
}