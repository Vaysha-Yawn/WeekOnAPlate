package week.on.a.plate.screens.createRecipe.state

import android.webkit.WebView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import java.time.LocalDateTime
import java.time.LocalTime

class RecipeCreateUIState {
    val activeTabIndex = mutableIntStateOf(0)
    val source = mutableStateOf("")
    val photoLink = mutableStateOf("")
    val mainImageContainer =  mutableStateOf<ImageBitmap?>(null)
    val name = mutableStateOf("")
    val description = mutableStateOf("")
    val portionsCount = mutableIntStateOf(0)
    val tags = mutableStateOf(listOf<RecipeTagView>())
    val ingredients = mutableStateOf(listOf<IngredientInRecipeView>())
    val steps = mutableStateOf(listOf<RecipeStepState>())
    val webview = mutableStateOf<WebView?>(null)
    val isForCreate = mutableStateOf<Boolean>(true)
    val duration = mutableStateOf<LocalTime>(LocalTime.of(0, 0))
}

class RecipeStepState(val id:Long) {
    val description = mutableStateOf("")
    val image = mutableStateOf("")
    val imageContainer =  mutableStateOf<ImageBitmap?>(null)
    val timer = mutableLongStateOf(0)
    val pinnedIngredientsInd:MutableState<List<Long>> = mutableStateOf(listOf())
}