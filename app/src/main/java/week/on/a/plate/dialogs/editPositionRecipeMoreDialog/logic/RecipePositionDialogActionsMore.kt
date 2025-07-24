package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.event.ActionMoreRecipePositionEvent
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.ChangePortionsRecipePosOpenDialog
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic.RecipeToShopListWithInventoryUseCase
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndMoveUseCase
import javax.inject.Inject


class RecipePositionDialogActionsMore @Inject constructor(
    private val getSelAndMove: GetSelAndMoveUseCase,
    private val changePortionsRecipePosOpenDialog: ChangePortionsRecipePosOpenDialog,
    private val deleteRecipe: DeleteRecipePosInDBUseCase,
    private val recipeToShopList: RecipeToShopListWithInventoryUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: ActionMoreRecipePositionEvent,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        when (event) {
            ActionMoreRecipePositionEvent.AddToCart ->
                recipeToShopList(
                    position, scope,
                    onEvent
                )

            ActionMoreRecipePositionEvent.ChangePotionsCount -> changePortionsRecipePosOpenDialog(
                position,
                dialogOpenParams, scope
            )

            ActionMoreRecipePositionEvent.Delete ->
                scope.launch(Dispatchers.IO) {
                    deleteRecipe(position)
                }

            //todo remember you have position
            hhhghh
            ActionMoreRecipePositionEvent.Move -> getSelAndMove(
                onEvent
            )

            ActionMoreRecipePositionEvent.CookPlan ->
                onEvent(
                    MainEvent.Navigate(
                        SpecifyForCookPlanDestination(
                            position.recipe.id,
                            position.portionsCount
                        )
                    )
                )

            ActionMoreRecipePositionEvent.Close -> {}
        }
    }
}