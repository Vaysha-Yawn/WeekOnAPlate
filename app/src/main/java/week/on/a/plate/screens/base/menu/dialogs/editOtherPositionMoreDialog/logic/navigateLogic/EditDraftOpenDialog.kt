package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.navigateLogic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterNavParams
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteDraftInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.UpdateDraftInDBUseCase
import javax.inject.Inject

class EditDraftOpenDialog @Inject constructor(
    private val deleteDraft: DeleteDraftInDBUseCase,
    private val editDraft: UpdateDraftInDBUseCase,
) {
    suspend operator fun invoke(
        oldDraft: Position.PositionDraftView,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        val params = FilterNavParams(
            FilterMode.Multiple,
            FilterEnum.IngredientAndTag, Pair(oldDraft.tags, oldDraft.ingredients), false
        ) { filters ->
            if (filters.tags?.isEmpty() == true && filters.ingredients?.isEmpty() == true || filters.ingredients == null || filters.tags == null) {
                launch(Dispatchers.IO) {
                    deleteDraft(oldDraft)
                }
            } else {
                launch(Dispatchers.IO) {
                    editDraft(oldDraft, filters.tags, filters.ingredients)
                }
            }
        }
        onEvent(MainEvent.Navigate(FilterDestination, params))
    }
}