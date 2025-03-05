package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.menu.event.MenuNavEvent
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DraftDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DraftMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.IngredientDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.IngredientMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.NoteDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.NoteMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.RecipeDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.RecipeMovePositionInMenuDB
import javax.inject.Inject

class GetSelAndUseCase @Inject constructor(
    private val draftMove: DraftMovePositionInMenuDB,
    private val recipeMove: RecipeMovePositionInMenuDB,
    private val ingredientMove: IngredientMovePositionInMenuDB,
    private val noteMove: NoteMovePositionInMenuDB,

    private val ingredientDouble: IngredientDoublePositionInMenuDB,
    private val recipeDouble: RecipeDoublePositionInMenuDB,
    private val draftDouble: DraftDoublePositionInMenuDB,
    private val noteDouble: NoteDoublePositionInMenuDB,
) {
    private fun specifyDate(
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit,
        use: (SpecifySelectionResult) -> Unit
    ) {
        mainViewModel.viewModelScope.launch {
            onEvent(MenuEvent.NavigateFromMenu(MenuNavEvent.SpecifySelection))
            mainViewModel.specifySelectionViewModel.launchAndGet(use)
        }
    }

    fun getSelAndCreate(context: Context, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            onEvent(MenuEvent.CreatePosition(res.selId, context))
        }
    }

    fun getSelAndMove(position: Position, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            mainViewModel.viewModelScope.launch(Dispatchers.IO) {
                when (position) {
                    is Position.PositionDraftView -> draftMove(position, res.selId)
                    is Position.PositionIngredientView -> ingredientMove(position, res.selId)
                    is Position.PositionNoteView -> noteMove(position, res.selId)
                    is Position.PositionRecipeView -> recipeMove(position, res.selId)
                }
            }
        }
    }

    fun getSelAndDouble(position: Position, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            mainViewModel.viewModelScope.launch(Dispatchers.IO) {
                when (position) {
                    is Position.PositionDraftView -> draftDouble(position, res.selId)
                    is Position.PositionIngredientView -> ingredientDouble(position, res.selId)
                    is Position.PositionNoteView -> noteDouble(position, res.selId)
                    is Position.PositionRecipeView -> recipeDouble(position, res.selId)
                }
            }
        }
    }
}
