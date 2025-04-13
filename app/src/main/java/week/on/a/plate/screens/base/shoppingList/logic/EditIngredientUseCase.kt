package week.on.a.plate.screens.base.shoppingList.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import javax.inject.Inject

class EditIngredientUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val checkInListToAdd: CheckInListToAddUseCase,
) {
    suspend operator fun invoke(
        ingredient: IngredientInRecipeView,
        dialogOpenParams: MutableState<DialogOpenParams?>
    ) = coroutineScope {
        val params = EditPositionIngredientViewModel.EditPositionIngredientDialogParams(
            Position.PositionIngredientView(
                0,
                ingredient,
                0
            ),
            false,

        ) { updatedIngredient ->
            launch {
                val item = shoppingItemRepository.getAll().find { it ->
                    it.ingredientInRecipe.id ==
                            ingredient.id
                } ?: return@launch
                if (updatedIngredient.ingredient.ingredientView.ingredientId != ingredient.ingredientView.ingredientId) {
                    checkInListToAdd(updatedIngredient.ingredient)
                } else {
                    shoppingItemRepository.update(
                        id = item.id,
                        ingredientInRecipeId = ingredient.id,
                        checked = false,
                        ingredientId = updatedIngredient.ingredient.ingredientView.ingredientId,
                        description = updatedIngredient.ingredient.description,
                        count = updatedIngredient.ingredient.count.toDouble()
                    )
                }
            }
        }
        dialogOpenParams.value = params
    }
}