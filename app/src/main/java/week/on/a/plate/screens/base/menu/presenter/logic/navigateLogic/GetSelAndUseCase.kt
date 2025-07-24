package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import androidx.compose.runtime.MutableState
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.base.menu.domain.dbusecase.DraftMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.IngredientMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.NoteMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.domain.dbusecase.RecipeMovePositionInMenuDB
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition.AddPositionOpenDialog
import javax.inject.Inject


class SpecifyDateNavToScreen @Inject constructor() {
    operator fun invoke(
        onEvent: (Event) -> Unit,
    ) {
        onEvent(MenuEvent.ClearSelected)
        onEvent(MainEvent.Navigate(SpecifySelectionDestination))
    }
}

class GetSelAndCreateUseCase @Inject constructor(
    private val specifyDate: SpecifyDateNavToScreen,
    private val addPosition: AddPositionOpenDialog,
) {
    operator fun invoke(
        onEvent: (Event) -> Unit,
    ) {
        specifyDate(onEvent)
    }

    suspend fun afterResult(
        selId: Long,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit,
    ) {
        addPosition(
            selId,
            dialogOpenParams,
            onEvent
        )
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
        onEvent: (Event) -> Unit,
        position: Position
    ) {
        specifyDate(onEvent)
    }

    suspend fun afterResult(
        selId: Long,
        position: Position,
    ) {
        when (position) {
            is Position.PositionDraftView -> draftMove(position, selId)
            is Position.PositionIngredientView -> ingredientMove(position, selId)
            is Position.PositionNoteView -> noteMove(position, selId)
            is Position.PositionRecipeView -> recipeMove(position, selId)
        }
    }
}
