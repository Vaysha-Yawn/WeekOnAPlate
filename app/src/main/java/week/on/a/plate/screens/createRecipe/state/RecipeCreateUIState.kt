package week.on.a.plate.screens.createRecipe.state

import android.webkit.WebView
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

class RecipeCreateUIState {
    val activeTabIndex = mutableIntStateOf(0)
    val source = mutableStateOf("")
    val photoLink = mutableStateOf("")
    val name = mutableStateOf("")
    val description = mutableStateOf("")
    val prepTime = mutableIntStateOf(0)
    val allTime = mutableIntStateOf(0)
    val portionsCount = mutableIntStateOf(0)
    val tags = mutableStateOf(listOf<RecipeTagView>())
    val ingredients = mutableStateOf(listOf<IngredientInRecipeView>())
    val steps = mutableStateOf(listOf<RecipeStepState>())
    val webview = mutableStateOf<WebView?>(null)
}

class RecipeStepState {
    val description = mutableStateOf("")
    val image = mutableStateOf("")
    val timer = mutableIntStateOf(0)
}