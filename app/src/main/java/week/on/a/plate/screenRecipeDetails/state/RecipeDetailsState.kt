package week.on.a.plate.screenRecipeDetails.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.RecipeView

class RecipeDetailsState(){
    val recipe:MutableState<RecipeView?> = mutableStateOf(null)
    val activeTabIndex = mutableIntStateOf(0)
}