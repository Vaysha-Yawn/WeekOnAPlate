package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.navigateLogic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
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
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        val vm = mainViewModel.filterViewModel
        vm.mainViewModel.nav.navigate(FilterDestination)
        vm.launchAndGet(
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
    }
}