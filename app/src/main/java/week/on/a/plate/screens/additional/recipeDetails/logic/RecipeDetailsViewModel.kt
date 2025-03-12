package week.on.a.plate.screens.additional.recipeDetails.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.room.cookPlanner.mapPinnedIngredients
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject


@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToMenuUseCase: AddToMenuUseCase,
    private val changePortionsManager: ChangePortionsManager,
    private val deleteUseCase: DeleteUseCase,
    private val editRecipeUseCase: EditRecipeUseCase,
    private val switchFavoriteUseCase: SwitchFavoriteUseCase,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            is RecipeDetailsEvent.AddToCart -> addToCartUseCase(
                event.context,
                mainViewModel,
                state,
                viewModelScope
            )

            RecipeDetailsEvent.AddToMenu -> viewModelScope.launch {
                addToMenuUseCase(mainViewModel, viewModelScope, state)
            }

            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> viewModelScope.launch {
                editRecipeUseCase(mainViewModel, viewModelScope, state, ::update)
            }

            RecipeDetailsEvent.MinusPortionsView -> changePortionsManager.minusPortionsView(
                state,
                ::update
            )

            RecipeDetailsEvent.PlusPortionsView -> changePortionsManager.plusPortionsView(
                state,
                ::update
            )

            RecipeDetailsEvent.SwitchFavorite -> viewModelScope.launch {
                switchFavoriteUseCase(state, ::update)
            }

            is RecipeDetailsEvent.Delete -> viewModelScope.launch {
                deleteUseCase(event.context, mainViewModel, state)
            }

            is RecipeDetailsEvent.Share -> ShareRecipeUseCase(event.context).shareRecipe(state.recipe)
        }
    }

    fun launch(recipeId: Long, portionsCount: Int? = null) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipe(recipeId)
            state.recipe = recipe
            state.isFavorite.value = recipe.inFavorite
            state.ingredientsCounts.value = recipe.ingredients
            state.currentPortions.intValue =
                portionsCount ?: recipe.standardPortionsCount

            state.mapPinnedStepIdToIngredients.value = recipe.steps.associate { stepView ->
                Pair(
                    stepView.id,
                    mapPinnedIngredients(
                        allIngredients = recipe.ingredients,
                        stdPortions = recipe.standardPortionsCount,
                        currentPortions = state.currentPortions.intValue,
                        ingredientsPinnedId = stepView.ingredientsPinnedId
                    )
                )
            }

            delay(600L)
            updIngredientsCount()
            mainViewModel.nav.navigate(RecipeDetailsDestination)
        }
    }

    private fun updIngredientsCount() {
        if (state.recipe.ingredients.size == state.ingredientsCounts.value.size && state.recipe.ingredients.isNotEmpty()) {
            state.ingredientsCounts.value = mapPinnedIngredients(
                allIngredients = state.recipe.ingredients,
                stdPortions = state.recipe.standardPortionsCount,
                currentPortions = state.currentPortions.intValue,
                ingredientsPinnedId = state.recipe.ingredients.map { it.ingredientView.ingredientId }
            )
        }
    }

    private fun update() {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipe(state.recipe.id)
            state.recipe = recipe
            updIngredientsCount()
            state.mapPinnedStepIdToIngredients.value = recipe.steps.associate { stepView ->
                Pair(
                    stepView.id,
                    mapPinnedIngredients(
                        allIngredients = recipe.ingredients,
                        stdPortions = recipe.standardPortionsCount,
                        currentPortions = state.currentPortions.intValue,
                        ingredientsPinnedId = stepView.ingredientsPinnedId
                    )
                )
            }
        }
    }

}