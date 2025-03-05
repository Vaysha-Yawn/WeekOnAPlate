package week.on.a.plate.screens.base.shoppingList.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

class DeleteApplyUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    suspend operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
    ) {
        val vmDel = mainViewModel.deleteApplyViewModel
        val mes = context.getString(R.string.hint_cannot_undone)
        mainViewModel.nav.navigate(DeleteApplyDestination)
        vmDel.launchAndGet(
            context,
            title = context.getString(R.string.hint_clear_shopping_list),
            message = mes
        ) { event ->
            if (event == DeleteApplyEvent.Apply) {
                shoppingItemRepository.deleteAll()
            }
        }
    }
}