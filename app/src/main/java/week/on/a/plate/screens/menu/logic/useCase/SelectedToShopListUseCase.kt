package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.state.MenuUIState
import javax.inject.Inject

class SelectedToShopListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        menuUIState: MenuUIState,
        onEvent: (MenuEvent) -> Unit,
        mainViewModel: MainViewModel,
        getSelected: (MenuUIState) -> List<Position.PositionRecipeView>
    ) {
        onEvent(MenuEvent.ActionWrapperDatePicker(WrapperDatePickerEvent.SwitchEditMode))
        val selected = getSelected(menuUIState)
        val list = selected.flatMap { positionRecipeView ->
            val currentPortions = positionRecipeView.portionsCount
            val recipe = recipeRepository.getRecipe(positionRecipeView.recipe.id)
            val standardCount = recipe.standardPortionsCount
            recipe.ingredients.map { ingredientInRecipeView ->
                ingredientInRecipeView.count =
                    ingredientInRecipeView.count / standardCount * currentPortions
                ingredientInRecipeView
            }
        }
        mainViewModel.inventoryViewModel.launchAndGetMore(list)
        mainViewModel.nav.navigate(InventoryDestination)
    }
}