package week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.event.ActionMoreRecipePositionEvent
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.ChangePortionsRecipePosOpenDialog
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.FindRecipeAndReplaceNavToScreen
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.NavToAddRecipeToCookPlan
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.RecipeToShopListWithInventoryUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
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
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: ActionMoreRecipePositionEvent,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        when (event) {
            ActionMoreRecipePositionEvent.AddToCart ->
                recipeToShopList(
                    position,
                    onEvent
                )

            ActionMoreRecipePositionEvent.ChangePotionsCount -> changePortionsRecipePosOpenDialog(
                position,
                dialogOpenParams
            )

            ActionMoreRecipePositionEvent.Delete ->
                launch(Dispatchers.IO) {
                    deleteRecipe(position)
                }

            ActionMoreRecipePositionEvent.Double -> getSelAndDouble(
                position,
                onEvent,
            )

            is ActionMoreRecipePositionEvent.FindReplace ->
                findRecipeAndReplace(
                    position, dialogOpenParams, event.context, onEvent
                )

            ActionMoreRecipePositionEvent.Move -> getSelAndMove(
                position,
                onEvent
            )

            ActionMoreRecipePositionEvent.CookPlan -> navToAddRecipeToCookPlan(
                position, onEvent
            )

            ActionMoreRecipePositionEvent.Close -> {}
        }
    }
}