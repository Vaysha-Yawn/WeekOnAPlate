package week.on.a.plate.screens.recipeDetails.logic

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class AddToCartUseCase @Inject constructor() {
    operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
        state: RecipeDetailsState, scope: CoroutineScope
    ) {
        if (state.recipe.ingredients.isNotEmpty()) {
            scope.launch {
                mainViewModel.nav.navigate(InventoryDestination)
                mainViewModel.inventoryViewModel.launchAndGet(state.ingredientsCounts.value)
            }
        } else {
            mainViewModel.onEvent(MainEvent.ShowSnackBar(context.getString(R.string.no_ingredients)))
        }
    }
}