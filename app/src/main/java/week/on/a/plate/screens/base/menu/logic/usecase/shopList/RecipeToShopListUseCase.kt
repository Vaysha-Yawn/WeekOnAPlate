package week.on.a.plate.screens.base.menu.logic.usecase.shopList

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import javax.inject.Inject

class RecipeToShopListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        positionRecipeView: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
    ) {
        val currentPortions = positionRecipeView.portionsCount
        val recipe = recipeRepository.getRecipe(positionRecipeView.recipe.id)
        val standardCount = recipe.standardPortionsCount
        val ingredients = recipe.ingredients.map { ingredientInRecipeView ->
            ingredientInRecipeView.count =
                ingredientInRecipeView.count / standardCount * currentPortions
            ingredientInRecipeView
        }
        mainViewModel.inventoryViewModel.launchAndGet(ingredients)
        mainViewModel.nav.navigate(InventoryDestination)
    }
}