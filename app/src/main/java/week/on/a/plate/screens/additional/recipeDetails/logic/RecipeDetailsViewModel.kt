package week.on.a.plate.screens.additional.recipeDetails.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.repository.room.cookPlanner.mapPinnedIngredients
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic.SwitchFavoriteUseCase
import week.on.a.plate.screens.additional.recipeDetails.logic.nav.AddToCartUseCase
import week.on.a.plate.screens.additional.recipeDetails.logic.nav.AddToMenuUseCase
import week.on.a.plate.screens.additional.recipeDetails.logic.nav.DeleteUseCase
import week.on.a.plate.screens.additional.recipeDetails.logic.nav.EditRecipeUseCase
import week.on.a.plate.screens.additional.recipeDetails.logic.utils.ChangePortionsManager
import week.on.a.plate.screens.additional.recipeDetails.logic.utils.ShareRecipeUseCase
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

    val mainEvent = mutableStateOf<MainEvent?>(null)
    val state = RecipeDetailsState()

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            is RecipeDetailsEvent.AddToCart ->
                viewModelScope.launch {
                    addToCartUseCase(
                        event.context,
                        { mainEvent.value = it },
                        state,
                    )
                }

            RecipeDetailsEvent.AddToMenu -> viewModelScope.launch {
                addToMenuUseCase(state) { mainEvent.value = it }
            }

            RecipeDetailsEvent.Back -> {
                mainEvent.value = MainEvent.NavigateBack
            }
            RecipeDetailsEvent.Edit -> viewModelScope.launch {
                editRecipeUseCase(state) {
                    mainEvent.value = it
                }
            }

            RecipeDetailsEvent.MinusPortionsView -> changePortionsManager.minusPortionsView(
                state,
                ::updIngredientsCount
            )

            RecipeDetailsEvent.PlusPortionsView -> changePortionsManager.plusPortionsView(
                state,
                ::updIngredientsCount
            )

            RecipeDetailsEvent.SwitchFavorite -> viewModelScope.launch {
                switchFavoriteUseCase(state.recipe)
            }

            is RecipeDetailsEvent.Delete -> viewModelScope.launch {
                deleteUseCase(event.context, state) {
                    mainEvent.value = it
                }
            }

            is RecipeDetailsEvent.Share -> ShareRecipeUseCase(event.context).shareRecipe(state.recipe)
        }
    }

    fun launch(recipeId: Long, portionsCount: Int? = null) {
        viewModelScope.launch {
            val recipeFlow = recipeRepository.getRecipeFlow(recipeId)
            recipeFlow.collect { recipe ->
                if (recipe != null) {
                    state.recipe = recipe
                    state.isFavorite.value = recipe.inFavorite
                    state.ingredients.value = recipe.ingredients
                    state.currentPortions.intValue = if (state.currentPortions.intValue == 0) {
                        portionsCount ?: recipe.standardPortionsCount
                    } else state.currentPortions.intValue

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
                    updIngredientsCount()
                }
            }
        }
    }


    private fun updIngredientsCount() {
        if (state.recipe.ingredients.size == state.ingredients.value.size && state.recipe.ingredients.isNotEmpty()) {
            state.ingredients.value = mapPinnedIngredients(
                allIngredients = state.recipe.ingredients,
                stdPortions = state.recipe.standardPortionsCount,
                currentPortions = state.currentPortions.intValue,
                ingredientsPinnedId = state.recipe.ingredients.map { it.ingredientView.ingredientId }
            )
        }
    }

}