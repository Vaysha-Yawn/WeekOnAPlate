package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition


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
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddDraftToDBUseCase
import javax.inject.Inject

class CreateDraftNavToScreen @Inject constructor(
    private val addDraft: AddDraftToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        val params = FilterNavParams(
            FilterMode.Multiple,
            FilterEnum.IngredientAndTag,
            null,
            false
        ) { filters ->
            if (filters.tags?.isEmpty() == true && filters.ingredients?.isEmpty() == true) return@FilterNavParams
            launch(Dispatchers.IO) {
                val draft =
                    Position.PositionDraftView(0, filters.tags!!, filters.ingredients!!, selId)
                addDraft(draft, selId)
            }
        }
        onEvent(MainEvent.Navigate(FilterDestination, params))
    }
}