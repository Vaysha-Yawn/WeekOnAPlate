package week.on.a.plate.screens.additional.recipeDetails.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class RecipeDetailsEvent: Event() {
    data object Edit: RecipeDetailsEvent()
    data object Back: RecipeDetailsEvent()
    data class AddToCart(val context: Context): RecipeDetailsEvent()
    data object SwitchFavorite: RecipeDetailsEvent()
    data object AddToMenu: RecipeDetailsEvent()
    data object PlusPortionsView: RecipeDetailsEvent()
    data object MinusPortionsView: RecipeDetailsEvent()
    data class Delete(val context: Context) : RecipeDetailsEvent()
    data class Share(val context: Context) : RecipeDetailsEvent()
}