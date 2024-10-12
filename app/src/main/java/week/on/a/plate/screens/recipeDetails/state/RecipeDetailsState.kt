package week.on.a.plate.screens.recipeDetails.state

import android.webkit.WebView
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.RecipeView

class RecipeDetailsState() {
    var recipe: State<RecipeView> = mutableStateOf(emptyRecipe)
    val ingredientsCounts = mutableStateOf<List<Int>>(listOf())
    val activeTabIndex = mutableIntStateOf(0)
    val currentPortions = mutableIntStateOf(2)
    val webview = mutableStateOf<WebView?>(null)
}