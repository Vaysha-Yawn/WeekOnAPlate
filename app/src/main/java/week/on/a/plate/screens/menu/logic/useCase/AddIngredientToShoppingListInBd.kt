package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.R
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.dialogs.chooseHowImagePick.event.BaseContextProvider
import week.on.a.plate.mainActivity.event.MainEvent

class AddIngredientToShoppingListInBd(private val shoppingItemRepository: ShoppingItemRepository) {
    suspend operator fun invoke(
        ingredientInRecipe: IngredientInRecipeView,
        contextProvider: BaseContextProvider, onEvent: (MainEvent) -> Unit
    ) {
        val allList = shoppingItemRepository.getAll()
        val haveItem =
            allList.find { it -> it.ingredientInRecipe.ingredientView.ingredientId == ingredientInRecipe.ingredientView.ingredientId }
        if (haveItem == null) {
            shoppingItemRepository.insert(ShoppingItemView(0, ingredientInRecipe, false))
        } else {
            if (haveItem.checked) {
                shoppingItemRepository.update(
                    haveItem.id,
                    haveItem.ingredientInRecipe.id,
                    false,
                    haveItem.ingredientInRecipe.ingredientView.ingredientId,
                    haveItem.ingredientInRecipe.description,
                    ingredientInRecipe.count.toDouble()
                )
            } else {
                shoppingItemRepository.update(
                    haveItem.id,
                    haveItem.ingredientInRecipe.id,
                    false,
                    haveItem.ingredientInRecipe.ingredientView.ingredientId,
                    haveItem.ingredientInRecipe.description,
                    ingredientInRecipe.count.toDouble() + haveItem.ingredientInRecipe.count.toDouble()
                )
            }
        }
        onEvent(
            MainEvent.ShowSnackBar(
                ingredientInRecipe.ingredientView.name + " " + contextProvider.context.getString(
                    R.string.add_to_shopping_list_succes
                )
            )
        )
    }
}