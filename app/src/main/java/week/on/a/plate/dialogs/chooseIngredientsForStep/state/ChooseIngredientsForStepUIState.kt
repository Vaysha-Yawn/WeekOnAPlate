package week.on.a.plate.dialogs.chooseIngredientsForStep.state

import androidx.compose.runtime.MutableState
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

class ChooseIngredientsForStepUIState(
    val ingredientsAll:List<IngredientInRecipeView>,
    val chosenIngredients: MutableState<List<Long>>
)


