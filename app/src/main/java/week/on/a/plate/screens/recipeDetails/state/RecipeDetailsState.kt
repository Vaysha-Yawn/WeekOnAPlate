package week.on.a.plate.screens.recipeDetails.state

import android.webkit.WebView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView

class RecipeDetailsState() {
    var recipe: RecipeView = emptyRecipe
    val ingredientsCounts = mutableStateOf<List<IngredientInRecipeView>>(listOf())
    val activeTabIndex = mutableIntStateOf(0)
    val currentPortions = mutableIntStateOf(2)
    val isFavorite = mutableStateOf<Boolean>(false)
    val mapPinnedStepIdToIngredients: MutableState<Map<Long, List<IngredientInRecipeView>>> = mutableStateOf(
        mapOf()
    )
    val webview = mutableStateOf<WebView?>(null)
}