package week.on.a.plate.screens.additional.recipeDetails.event

import android.content.Context
import week.on.a.plate.core.Event

sealed interface RecipeDetailsEvent : Event {
    object Edit : RecipeDetailsEvent
    object Back : RecipeDetailsEvent
    class AddToCart(val context: Context) : RecipeDetailsEvent
    object SwitchFavorite : RecipeDetailsEvent
    object AddToMenu : RecipeDetailsEvent
    object PlusPortionsView : RecipeDetailsEvent
    object MinusPortionsView : RecipeDetailsEvent
    class Delete(val context: Context) : RecipeDetailsEvent
    class Share(val context: Context) : RecipeDetailsEvent
}