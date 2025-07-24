package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.screens.additional.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class AddToCartUseCase @Inject constructor() {
    suspend operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit,
        scope: CoroutineScope,
        state: RecipeDetailsState
    ) = coroutineScope {
        if (state.recipe.ingredients.isNotEmpty()) {
            scope.launch(Dispatchers.IO) {
                onEvent(MainEvent.Navigate(InventoryDestination(state.ingredients.value)))
            }
        } else {
            onEvent(MainEvent.ShowSnackBar(context.getString(R.string.no_ingredients)))
        }
    }
}