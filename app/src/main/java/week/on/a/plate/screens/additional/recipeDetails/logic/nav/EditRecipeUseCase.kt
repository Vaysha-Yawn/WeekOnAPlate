package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateNavParams
import week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic.EditRecipeUseCaseDB
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import java.time.LocalDateTime
import javax.inject.Inject

class EditRecipeUseCase @Inject constructor(
    private val editRecipeUseCaseDB: EditRecipeUseCaseDB
) {
    suspend operator fun invoke(
        state: RecipeDetailsState, onEvent: (MainEvent) -> Unit,
    ) = coroutineScope {
        val params = RecipeCreateNavParams(state.recipe, false) { recipe ->
            launch {
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
                editRecipeUseCaseDB(state.recipe, newRecipe)
            }
        }
        onEvent(MainEvent.Navigate(RecipeCreateDestination, params))
    }
}