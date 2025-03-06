package week.on.a.plate.screens.base.menu.logic.usecase

import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider
import javax.inject.Inject

//todo slice for use cases
class AddIngredientToShoppingListInBd @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository
) {
    suspend operator fun invoke(
        ingredientInRecipe: IngredientInRecipeView,
        contextProvider: BaseContextProvider, onEvent: (MainEvent) -> Unit
    ) {
        val allList = shoppingItemRepository.getAll()//1
        val haveItem =
            allList.find { it -> it.ingredientInRecipe.ingredientView.ingredientId == ingredientInRecipe.ingredientView.ingredientId }
        if (haveItem == null) {
            //ингредиента ещё нет в списке
            shoppingItemRepository.insert(ShoppingItemView(0, ingredientInRecipe, false))//2
        } else {
            // ингредиент есть в списке
            if (haveItem.checked) {
                shoppingItemRepository.update(
                    haveItem.id,
                    haveItem.ingredientInRecipe.id,
                    false,
                    haveItem.ingredientInRecipe.ingredientView.ingredientId,
                    haveItem.ingredientInRecipe.description,
                    ingredientInRecipe.count.toDouble()
                )//3
            } else {
                shoppingItemRepository.update(
                    haveItem.id,
                    haveItem.ingredientInRecipe.id,
                    false,
                    haveItem.ingredientInRecipe.ingredientView.ingredientId,
                    haveItem.ingredientInRecipe.description,
                    ingredientInRecipe.count.toDouble() + haveItem.ingredientInRecipe.count.toDouble()
                )//4
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