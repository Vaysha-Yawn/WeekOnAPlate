package week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.event.ActionMoreRecipePositionEvent
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.ChangePortionsRecipePosOpenDialog
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.FindRecipeAndReplaceNavToScreen
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.NavToAddRecipeToCookPlan
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.RecipeToShopListWithInventoryUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndDoubleUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndMoveUseCase
import javax.inject.Inject


class RecipePositionDialogActionsMore @Inject constructor(
    private val getSelAndMove: GetSelAndMoveUseCase,
    private val getSelAndDouble: GetSelAndDoubleUseCase,
    private val changePortionsRecipePosOpenDialog: ChangePortionsRecipePosOpenDialog,
    private val navToAddRecipeToCookPlan: NavToAddRecipeToCookPlan,
    private val deleteRecipe: DeleteRecipePosInDBUseCase,
    private val findRecipeAndReplace: FindRecipeAndReplaceNavToScreen,
    private val recipeToShopList: RecipeToShopListWithInventoryUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
        event: ActionMoreRecipePositionEvent,
        onEventMenu: (MenuEvent) -> Unit
    ) = coroutineScope {
        when (event) {
            ActionMoreRecipePositionEvent.AddToCart ->
                recipeToShopList(
                    position,
                    mainViewModel
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
                mainViewModel,
                onEventMenu
            )

            is ActionMoreRecipePositionEvent.FindReplace ->
                findRecipeAndReplace(
                    position,
                    mainViewModel, event.context
                )

            ActionMoreRecipePositionEvent.Move -> getSelAndMove(
                position,
                mainViewModel,
                onEventMenu
            )

            ActionMoreRecipePositionEvent.CookPlan -> navToAddRecipeToCookPlan(
                position, mainViewModel
            )

            ActionMoreRecipePositionEvent.Close -> {}
        }
    }
}