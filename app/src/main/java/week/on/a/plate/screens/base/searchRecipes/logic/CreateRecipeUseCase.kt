package week.on.a.plate.screens.base.searchRecipes.logic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateNavParams
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class CreateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        state: SearchUIState, onEvent: (MainEvent) -> Unit
    ) = coroutineScope {
        val recipeStart = getRecipeBaseFromSearchState(state)
        val params = RecipeCreateNavParams(recipeStart, true) { recipe ->
            launch {
                val newRecipe = getRecipeFromCreateState(recipe)
                recipeRepository.create(newRecipe)
            }
        }

        onEvent(MainEvent.Navigate(RecipeCreateDestination, params))
    }

    private fun getRecipeFromCreateState(recipe: RecipeCreateUIState): RecipeView {
        val newRecipe = RecipeView(
            id = 0,
            name = recipe.name.value,
            description = recipe.description.value,
            img = recipe.photoLink.value,
            tags = recipe.tags.value,
            standardPortionsCount = recipe.portionsCount.intValue,
            ingredients = recipe.ingredients.value,
            steps = recipe.steps.value.map {
                RecipeStepView(
                    0,
                    it.description.value,
                    it.image.value,
                    it.timer.longValue, it.pinnedIngredientsInd.value
                )
            },
            link = recipe.source.value,
            false,
            LocalDateTime.now(),
            recipe.duration.value
        )
        return newRecipe
    }

    private fun getRecipeBaseFromSearchState(state: SearchUIState): RecipeView {
        val listRecipe = mutableListOf<IngredientInRecipeView>()
        state.selectedIngredients.value.forEach {
            val ingredient = IngredientInRecipeView(0, it, "", 0)
            listRecipe.add(ingredient)
        }
        val recipeStart = RecipeView(
            id = 0,
            name = state.searchText.value,
            description = "",
            img = "",
            tags = state.selectedTags.value,
            standardPortionsCount = 4,
            ingredients = listRecipe,
            steps = listOf(),
            link = "", false, LocalDateTime.now(), LocalTime.of(0, 0)
        )
        return recipeStart
    }


}