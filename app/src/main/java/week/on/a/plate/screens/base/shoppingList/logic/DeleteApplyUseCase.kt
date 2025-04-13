package week.on.a.plate.screens.base.shoppingList.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyNavParams
import javax.inject.Inject

class DeleteApplyUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    suspend operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit
    ) {
        val mes = context.getString(R.string.hint_cannot_undone)
        val params = DeleteApplyNavParams(
            context,
            title = context.getString(R.string.hint_clear_shopping_list),
            message = mes
        ) { event ->
            if (event == DeleteApplyEvent.Apply) {
                shoppingItemRepository.deleteAll()
            }
        }
        onEvent(MainEvent.Navigate(DeleteApplyDestination, params))
    }
}