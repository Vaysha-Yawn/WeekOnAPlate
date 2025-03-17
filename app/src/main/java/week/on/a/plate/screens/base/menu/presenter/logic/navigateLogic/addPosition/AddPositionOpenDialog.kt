package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import android.content.Context
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
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