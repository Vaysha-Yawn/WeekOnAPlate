package week.on.a.plate.screens.additional.inventory.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.core.navigation.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


@Serializable
data class InventoryDestination(val list: List<IngredientInRecipeView>)