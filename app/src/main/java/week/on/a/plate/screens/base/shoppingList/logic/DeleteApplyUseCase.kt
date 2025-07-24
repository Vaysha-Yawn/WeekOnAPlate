package week.on.a.plate.screens.base.shoppingList.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

class DeleteApplyUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit
    ) {
        val title = context.getString(R.string.hint_clear_shopping_list)
        val mes = context.getString(R.string.hint_cannot_undone)
        onEvent(MainEvent.Navigate(DeleteApplyDestination(title, mes)))
    }

    suspend fun doAfterDeleteApply() {
        shoppingItemRepository.deleteAll()
    }
}