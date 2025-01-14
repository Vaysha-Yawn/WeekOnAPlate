package week.on.a.plate.screens.recipeDetails.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import java.time.LocalDateTime
import javax.inject.Inject

class EditRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        mainViewModel: MainViewModel,
        viewModelScope: CoroutineScope,
        state: RecipeDetailsState,
        update: () -> Unit
    ) {
        val vm = mainViewModel.recipeCreateViewModel
        mainViewModel.nav.navigate(RecipeCreateDestination)
        vm.launchAndGet(state.recipe, false) { recipe ->
            viewModelScope.launch {
                val newRecipe = RecipeView(
                    id = state.recipe.id,
                    name = recipe.name.value,
                    description = recipe.description.value,
                    img = recipe.photoLink.value,
                    tags = recipe.tags.value,
                    standardPortionsCount = recipe.portionsCount.intValue,
                    ingredients = recipe.ingredients.value,
                    steps = recipe.steps.value.map {
                        RecipeStepView(
                            it.id,
                            it.description.value,
                            it.image.value,
                            it.timer.longValue, it.pinnedIngredientsInd.value
                        )
                    },
                    link = recipe.source.value,
                    state.recipe.inFavorite,
                    LocalDateTime.now(),
                    recipe.duration.value
                )
                recipeRepository.updateRecipe(state.recipe, newRecipe)
                update()
            }
        }
    }
}