package week.on.a.plate.screens.createRecipe.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.recipeDetails.view.ingredients.IngredientInRecipeCard

@Composable
fun IngredientRecipeEdit(
    ingredient: IngredientInRecipeView,
    state: RecipeCreateUIState,
    onEvent: (RecipeCreateEvent) -> Unit
) {
    IngredientInRecipeCard(ingredient, null, {
        onEvent(RecipeCreateEvent.EditIngredient(ingredient))
    }, true){onEvent(RecipeCreateEvent.DeleteIngredient(ingredient)) }
}

@Preview(showBackground = true)
@Composable
fun PreviewIngredientsRecipeEdit() {
    WeekOnAPlateTheme {
        IngredientRecipeEdit(recipeTom.ingredients[0], RecipeCreateUIState()) {}
    }
}