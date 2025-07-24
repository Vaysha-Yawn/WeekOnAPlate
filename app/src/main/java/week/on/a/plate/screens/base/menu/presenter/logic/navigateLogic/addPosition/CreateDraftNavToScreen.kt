package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition


import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddDraftToDBUseCase
import javax.inject.Inject

class CreateDraftNavToScreen @Inject constructor(
    private val addDraft: AddDraftToDBUseCase
) {
    operator fun invoke(
        selId: Long,
        onEvent: (Event) -> Unit
    ) {
        onEvent(
            MainEvent.Navigate(
                FilterDestination(
                    FilterMode.Multiple,
                    FilterEnum.IngredientAndTag,
                    null,
                    false
                )
            )
        )
    }

    //todo use
    jyyjyjy
    suspend fun afterResult(
        res: FilterResult, selId: Long,
    ) {
        if (res.tags?.isEmpty() == true && res.ingredients?.isEmpty() == true) return
        val draft =
            Position.PositionDraftView(0, res.tags!!, res.ingredients!!, selId)
        addDraft(draft, selId)
    }
}