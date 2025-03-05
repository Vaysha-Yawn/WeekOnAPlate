package week.on.a.plate.screens.base.shoppingList.logic

import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import javax.inject.Inject

class DeleteCheckedUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    suspend operator fun invoke() {
        shoppingItemRepository.deleteAllChecked()
    }
}