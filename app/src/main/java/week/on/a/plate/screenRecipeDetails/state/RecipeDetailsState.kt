package week.on.a.plate.screenRecipeDetails.state

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.data.dataView.recipe.RecipeView

class RecipeDetailsState() {
    var recipe: State<RecipeView> = mutableStateOf(emptyRecipe)
    val ingredientsCounts = mutableStateOf<List<Int>>(listOf())
    val activeTabIndex = mutableIntStateOf(0)
    val currentPortions = mutableIntStateOf(2)
}