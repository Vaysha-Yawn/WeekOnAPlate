package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import android.content.Context
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft.NavToScreenCreateDraft
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient.OpenDialogForAddIngredient
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.note.OpenDialogCreateNote
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe.NavToScreenForAddRecipe
import javax.inject.Inject

class OpenDialogAddPosition @Inject constructor(
    private val openDialogCreateNote: OpenDialogCreateNote,
    private val navToScreenForAddRecipe: NavToScreenForAddRecipe,
    private val navToScreenCreateDraft: NavToScreenCreateDraft,
    private val openDialogForAddIngredient: OpenDialogForAddIngredient,
) {
    suspend operator fun invoke(
        selId: Long, context: Context,
        mainViewModel: MainViewModel
    ) = coroutineScope {
        AddPositionViewModel.launch(mainViewModel) { event ->
            launch {
                when (event) {
                    AddPositionEvent.AddDraft -> navToScreenCreateDraft(selId, mainViewModel)
                    AddPositionEvent.AddIngredient -> openDialogForAddIngredient(
                        selId,
                        mainViewModel
                    )

                    AddPositionEvent.AddNote -> openDialogCreateNote(selId, mainViewModel)
                    AddPositionEvent.AddRecipe -> navToScreenForAddRecipe(
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