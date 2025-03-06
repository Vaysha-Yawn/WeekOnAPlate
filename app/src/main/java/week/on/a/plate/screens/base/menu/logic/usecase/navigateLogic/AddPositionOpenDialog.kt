package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft.CreateDraftNavToScreen
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient.AddIngredientOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.note.CreateNoteOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe.AddRecipeNavToScreen
import javax.inject.Inject

class AddPositionOpenDialog @Inject constructor(
    private val createNoteOpenDialog: CreateNoteOpenDialog,
    private val addRecipeNavToScreen: AddRecipeNavToScreen,
    private val createDraftNavToScreen: CreateDraftNavToScreen,
    private val addIngredientOpenDialog: AddIngredientOpenDialog,
) {
    suspend operator fun invoke(
        selId: Long, context: Context,
        mainViewModel: MainViewModel
    ) = coroutineScope {
        AddPositionViewModel.launch(mainViewModel) { event ->
            launch {
                when (event) {
                    AddPositionEvent.AddDraft -> createDraftNavToScreen(selId, mainViewModel)
                    AddPositionEvent.AddIngredient -> addIngredientOpenDialog(selId, mainViewModel)
                    AddPositionEvent.AddNote -> createNoteOpenDialog(selId, mainViewModel)
                    AddPositionEvent.AddRecipe -> addRecipeNavToScreen(
                        selId,
                        context,
                        mainViewModel
                    )
                    AddPositionEvent.Close -> {}
                }
            }
        }
    }
}