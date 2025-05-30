package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionParams
import week.on.a.plate.screens.base.menu.domain.dbusecase.DraftDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.DraftMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.IngredientDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.IngredientMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.NoteDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.NoteMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.RecipeDoublePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.RecipeMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition.AddPositionOpenDialog
import javax.inject.Inject


class SpecifyDateNavToScreen @Inject constructor() {
    operator fun invoke(
        onEvent: (Event) -> Unit,
        use: (SpecifySelectionResult) -> Unit
    ) {
        onEvent(MenuEvent.ClearSelected)
        onEvent(MainEvent.Navigate(SpecifySelectionDestination, SpecifySelectionParams(use)))
    }
}

class GetSelAndCreateUseCase @Inject constructor(
    private val specifyDate: SpecifyDateNavToScreen,
    private val addPosition: AddPositionOpenDialog,
) {
    suspend operator fun invoke(
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit,
    ) = coroutineScope {
        specifyDate(onEvent) { res ->
            scope.launch(Dispatchers.IO) {
                addPosition(
                    res.selId,
                    context,
                    dialogOpenParams,
                    onEvent
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
    suspend operator fun invoke(
        position: Position,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit,
    ) = coroutineScope {
        specifyDate(onEvent) { res ->
            scope.launch(Dispatchers.IO) {
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

    suspend operator fun invoke(
        position: Position,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit,
    ) = coroutineScope {
        specifyDate(onEvent) { res ->
            scope.launch(Dispatchers.IO) {
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
