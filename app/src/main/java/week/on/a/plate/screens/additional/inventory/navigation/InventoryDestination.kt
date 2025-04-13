package week.on.a.plate.screens.additional.inventory.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


@Serializable
data object InventoryDestination

class InventoryNavParams(private val list: List<IngredientInRecipeView>) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.inventoryViewModel.launchAndGetMore(list)
    }
}