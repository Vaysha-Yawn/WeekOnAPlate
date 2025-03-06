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
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteDraftInDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteIngredientPositionInDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteNoteInDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndDoubleUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndMoveUseCase
import javax.inject.Inject

//todo slice, а точнее добавить в OtherPositionActionsMore View model все use case с действиями и в onEvent вызывать use case
class OtherPositionActionsMore @Inject constructor(
    private val addIngredientToShoppingListInBd: AddIngredientToShoppingListInBd,
    private val getSelAndDouble: GetSelAndDoubleUseCase,
    private val getSelAndMove: GetSelAndMoveUseCase,

    private val deleteDraft: DeleteDraftInDBUseCase,
    private val deleteIngredient: DeleteIngredientPositionInDBUseCase,
    private val deleteNote: DeleteNoteInDBUseCase,
    private val deleteRecipe: DeleteRecipePosInDBUseCase,
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
                OtherPositionMoreEvent.Delete ->
                    launch {
                        when (position) {
                            is Position.PositionDraftView -> deleteDraft(position)
                            is Position.PositionIngredientView -> deleteIngredient(position)
                            is Position.PositionNoteView -> deleteNote(position)
                            is Position.PositionRecipeView -> deleteRecipe(position)
                        }
                    }

                OtherPositionMoreEvent.Double -> getSelAndDouble(
                    position,
                    mainViewModel
                )

                OtherPositionMoreEvent.Edit -> onEventMenu(MenuEvent.EditOtherPosition(position))
                OtherPositionMoreEvent.Move -> getSelAndMove(
                    position,
                    mainViewModel
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