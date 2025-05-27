package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.additional.inventory.navigation.InventoryNavParams
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetRecipeUseCase
import week.on.a.plate.screens.base.menu.domain.utilsUseCase.IngredientsMapByPortionsUseCase
import javax.inject.Inject

class RecipeToShopListWithInventoryUseCase @Inject constructor(
    private val getRecipe: GetRecipeUseCase,
    private val ingredientsMapByPortions: IngredientsMapByPortionsUseCase
) {
    suspend operator fun invoke(
        positionRecipeView: Position.PositionRecipeView,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        val recipe = async { getRecipe(positionRecipeView.recipe.id) }
        val ingredients = ingredientsMapByPortions(positionRecipeView.portionsCount, recipe.await())
        val params = InventoryNavParams(ingredients)
        onEvent(MainEvent.Navigate(InventoryDestination, params))
    }
}