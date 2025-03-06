package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DraftDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DraftMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.IngredientDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.IngredientMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.NoteDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.NoteMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.RecipeDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.RecipeMovePositionInMenuDB
import javax.inject.Inject


class SpecifyDateNavToScreen @Inject constructor() {
    operator fun invoke(
        mainViewModel: MainViewModel,
        use: (SpecifySelectionResult) -> Unit
    ) {
        mainViewModel.viewModelScope.launch {
            //todo selectedRecipeManager.clear(menuUIState)
            //todo specifySelection - временный экран
            mainViewModel.nav.navigate(SpecifySelectionDestination)
            mainViewModel.specifySelectionViewModel.launchAndGet(use)
        }
    }
}

class GetSelAndCreateUseCase @Inject constructor(
    private val specifyDate: SpecifyDateNavToScreen,
    private val addPosition: AddPositionOpenDialog,
) {
    operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
    ) {
        specifyDate(mainViewModel) { res ->
            mainViewModel.viewModelScope.launch {
                addPosition(
                    res.selId,
                    context,
                    mainViewModel
                )
            }
        }
    }
}

class GetSelAndMoveUseCase @Inject constructor(
    private val specifyDate: SpecifyDateNavToScreen,
    private val draftMove: DraftMovePositionInMenuDB,
    private val recipeMove: RecipeMovePositionInMenuDB,
    private val ingredientMove: IngredientMovePositionInMenuDB,
    private val noteMove: NoteMovePositionInMenuDB,
) {
    operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
    ) {
        specifyDate(mainViewModel) { res ->
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
}

class GetSelAndDoubleUseCase @Inject constructor(
    private val specifyDate: SpecifyDateNavToScreen,

    private val ingredientDouble: IngredientDoublePositionInMenuDB,
    private val recipeDouble: RecipeDoublePositionInMenuDB,
    private val draftDouble: DraftDoublePositionInMenuDB,
    private val noteDouble: NoteDoublePositionInMenuDB,
) {

    operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
    ) {
        specifyDate(mainViewModel) { res ->
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
