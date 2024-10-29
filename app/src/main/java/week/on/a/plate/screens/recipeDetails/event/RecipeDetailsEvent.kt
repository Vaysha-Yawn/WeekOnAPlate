package week.on.a.plate.screens.recipeDetails.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class RecipeDetailsEvent: Event() {
    data object Edit: RecipeDetailsEvent()
    data object Back: RecipeDetailsEvent()
    data object AddToCart: RecipeDetailsEvent()
    data object SwitchFavorite: RecipeDetailsEvent()
    data object AddToMenu: RecipeDetailsEvent()
    data object PlusPortionsView: RecipeDetailsEvent()
    data object MinusPortionsView: RecipeDetailsEvent()
    data object Delete : RecipeDetailsEvent()
    data class Share(val context: Context) : RecipeDetailsEvent()
}