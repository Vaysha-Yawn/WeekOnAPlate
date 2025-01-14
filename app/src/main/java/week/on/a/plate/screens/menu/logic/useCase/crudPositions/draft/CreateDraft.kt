package week.on.a.plate.screens.menu.logic.useCase.crudPositions.draft


import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class CreateDraft @Inject constructor() {
    //addDraft
    suspend operator fun invoke(
        selId: Long,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
        val vm = mainViewModel.filterViewModel
        vm.mainViewModel.nav.navigate(FilterDestination)
        vm.launchAndGet(
            FilterMode.Multiple,
            FilterEnum.IngredientAndTag,
            null,
            false
        ) { filters ->
            if (filters.tags?.isEmpty() == true && filters.ingredients?.isEmpty() == true) return@launchAndGet
            val draft =
                Position.PositionDraftView(0, filters.tags!!, filters.ingredients!!, selId)
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddDraft(draft)))
        }
    }
}