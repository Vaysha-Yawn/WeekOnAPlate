package week.on.a.plate.screens.menu.logic.useCase.crudPositions.draft

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class EditDraft @Inject constructor() {
    suspend operator fun invoke(
        oldDraft: Position.PositionDraftView,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
            val vm = mainViewModel.filterViewModel
            vm.mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(
                FilterMode.Multiple,
                FilterEnum.IngredientAndTag, Pair(oldDraft.tags, oldDraft.ingredients), false
            ) { filters ->
                if (filters.tags?.isEmpty() == true && filters.ingredients?.isEmpty() == true || filters.ingredients == null || filters.tags == null) {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(oldDraft)))
                } else {
                    onEvent(
                        MenuEvent.ActionDBMenu(
                            ActionWeekMenuDB.EditDraft(
                                oldDraft,
                                Pair(filters.tags, filters.ingredients)
                            )
                        )
                    )
                }
            }
    }
}