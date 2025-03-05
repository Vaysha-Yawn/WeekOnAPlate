package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddDraftToDBUseCase
import javax.inject.Inject

class NavToScreenCreateDraft @Inject constructor(
    private val addDraft: AddDraftToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long,
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        val vm = mainViewModel.filterViewModel
        vm.mainViewModel.nav.navigate(FilterDestination)
        vm.launchAndGet(
            FilterMode.Multiple,
            FilterEnum.IngredientAndTag,
            null,
            false
        ) { filters ->
            if (filters.tags?.isEmpty() == true && filters.ingredients?.isEmpty() == true) return@launchAndGet
            launch(Dispatchers.IO) {
                val draft =
                    Position.PositionDraftView(0, filters.tags!!, filters.ingredients!!, selId)
                addDraft(draft, selId)
            }
        }
    }
}