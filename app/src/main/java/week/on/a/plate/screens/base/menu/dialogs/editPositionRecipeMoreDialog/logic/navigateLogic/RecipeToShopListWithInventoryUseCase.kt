package week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetRecipeUseCase
import week.on.a.plate.screens.base.menu.domain.utilsUseCase.IngredientsMapByPortionsUseCase
import javax.inject.Inject

//todo может быть тут создать, инициализировать InventoryViewModel так как возникает необходимость,
// чтобы не хранить ее всегда
class RecipeToShopListWithInventoryUseCase @Inject constructor(
    private val getRecipe: GetRecipeUseCase,
    private val ingredientsMapByPortions: IngredientsMapByPortionsUseCase
) {
    suspend operator fun invoke(
        positionRecipeView: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        val recipe = async { getRecipe(positionRecipeView.recipe.id) }
        val ingredients = ingredientsMapByPortions(positionRecipeView.portionsCount, recipe.await())

        mainViewModel.inventoryViewModel.launchAndGet(ingredients)
        mainViewModel.nav.navigate(InventoryDestination)
    }
}