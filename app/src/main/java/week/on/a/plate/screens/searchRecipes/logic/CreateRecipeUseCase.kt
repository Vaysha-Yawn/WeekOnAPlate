package week.on.a.plate.screens.searchRecipes.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.searchRecipes.state.SearchUIState
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class CreateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        mainViewModel: MainViewModel,state:SearchUIState, viewModelScope:CoroutineScope
    ) {
        val vm = mainViewModel.recipeCreateViewModel
        mainViewModel.nav.navigate(RecipeCreateDestination)

        val recipeStart = getRecipeBaseFromSearchState(state)

        vm.launchAndGet(recipeStart, true) { recipe ->
            viewModelScope.launch {
                val newRecipe = getRecipeFromCreateState(recipe)
                recipeRepository.create(newRecipe)
            }
        }
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