package week.on.a.plate.app.mainActivity.logic

import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetSharedLinkUseCase
@Inject constructor (private val recipeRepository: RecipeRepository) {

    private var sharedLink = ""
    var isCheckedSharedAction = false

    fun setLink(text:String){
        sharedLink = text
    }

    fun checkAndStart(use:()->Unit){
        if (sharedLink != "" && !isCheckedSharedAction) {
            use()
        }
    }


    fun useSharedLink(scope:CoroutineScope, nav: NavHostController,
                      recipeCreateViewModel: RecipeCreateViewModel
    ) {
        nav.navigate(RecipeCreateDestination)
        isCheckedSharedAction = true
        val recipeBase = emptyRecipe.apply {
            link = sharedLink
        }
        scope.launch {
            recipeCreateViewModel.launchAndGet(recipeBase, true) { recipe ->
                insertRecipeToDB(recipe, scope)
            }
        }
    }

    private fun insertRecipeToDB(recipe: RecipeCreateUIState, scope: CoroutineScope) {
        scope.launch {
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
                        it.timer.longValue,
                        it.pinnedIngredientsInd.value
                    )
                },
                link = recipe.source.value, false, LocalDateTime.now(),
                recipe.duration.value
            )
            recipeRepository.create(newRecipe)
        }
    }

}
