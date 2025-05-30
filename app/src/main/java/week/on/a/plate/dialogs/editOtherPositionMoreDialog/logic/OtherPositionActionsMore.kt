package week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.navigateLogic.EditDraftOpenDialog
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.navigateLogic.EditIngredientOpenDialog
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.navigateLogic.EditNoteOpenDialog
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteDraftInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteIngredientPositionInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteNoteInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndDoubleUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndMoveUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.shopList.AddIngredientToShoppingListInBd
import javax.inject.Inject

class OtherPositionActionsMore @Inject constructor(
    private val addIngredientToShoppingListInBd: AddIngredientToShoppingListInBd,
    private val getSelAndDouble: GetSelAndDoubleUseCase,
    private val getSelAndMove: GetSelAndMoveUseCase,

    private val deleteDraft: DeleteDraftInDBUseCase,
    private val deleteIngredient: DeleteIngredientPositionInDBUseCase,
    private val deleteNote: DeleteNoteInDBUseCase,
    private val deleteRecipe: DeleteRecipePosInDBUseCase,

    private val editNoteOpenDialog: EditNoteOpenDialog,
    private val editDraftOpenDialog: EditDraftOpenDialog,
    private val editIngredientOpenDialog: EditIngredientOpenDialog,
) {
    suspend operator fun invoke(
        position: Position,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit,
        scope: CoroutineScope,
        event: OtherPositionMoreEvent,
    ) = coroutineScope {
        when (event) {
            OtherPositionMoreEvent.Close -> {}
            OtherPositionMoreEvent.Delete ->
                scope.launch {
                    when (position) {
                        is Position.PositionDraftView -> deleteDraft(position)
                        is Position.PositionIngredientView -> deleteIngredient(position)
                        is Position.PositionNoteView -> deleteNote(position)
                        is Position.PositionRecipeView -> deleteRecipe(position)
                    }
                }

            OtherPositionMoreEvent.Double -> getSelAndDouble(
                position, scope,
                onEvent,
            )

            OtherPositionMoreEvent.Edit ->
                scope.launch {
                    when (position) {
                        is Position.PositionDraftView -> editDraftOpenDialog(
                            position, scope,
                            onEvent
                        )

                        is Position.PositionIngredientView -> editIngredientOpenDialog(
                            position,
                            dialogOpenParams, scope
                        )

                        is Position.PositionNoteView -> editNoteOpenDialog(
                            position,
                            dialogOpenParams, scope
                        )

                        is Position.PositionRecipeView -> {}
                    }
                }

            OtherPositionMoreEvent.Move -> getSelAndMove(
                position, scope,
                onEvent
            )

            is OtherPositionMoreEvent.AddToShopList -> {
                scope.launch {
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
                            onEvent
                        )
                    }
                }
            }
        }
    }
}