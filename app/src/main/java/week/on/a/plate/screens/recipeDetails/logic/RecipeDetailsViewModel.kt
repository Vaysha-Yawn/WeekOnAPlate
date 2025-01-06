package week.on.a.plate.screens.recipeDetails.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.cookPlanner.mapPinnedIngredients
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.inventory.navigation.InventoryDestination
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDestination
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            is RecipeDetailsEvent.AddToCart -> addToCart(event.context)
            RecipeDetailsEvent.AddToMenu -> addToMenu()
            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> editRecipe()
            RecipeDetailsEvent.MinusPortionsView -> minusPortionsView()
            RecipeDetailsEvent.PlusPortionsView -> plusPortionsView()
            RecipeDetailsEvent.SwitchFavorite -> switchFavorite()
            is RecipeDetailsEvent.Delete -> delete(event.context)
            is RecipeDetailsEvent.Share -> share(event.context)
        }
    }

    private fun share(context: Context) {
        ShareRecipeUseCase(context).shareRecipe(state.recipe)
    }

    private fun addToCart(context: Context) {
        if (state.recipe.ingredients.isNotEmpty()) {
            viewModelScope.launch {
                mainViewModel.nav.navigate(InventoryDestination)
                mainViewModel.inventoryViewModel.launchAndGet(state.ingredientsCounts.value)
            }
        } else {
            mainViewModel.onEvent(MainEvent.ShowSnackBar(context.getString(R.string.no_ingredients)))
        }
    }

    private fun plusPortionsView() {
        state.currentPortions.intValue = state.currentPortions.intValue.plus(1)
        update()
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

    private fun minusPortionsView() {
        if (state.currentPortions.intValue > 1) {
            state.currentPortions.intValue = state.currentPortions.intValue.minus(1)
            update()
        }
    }

    private fun delete(context: Context) {
        viewModelScope.launch {
            val vm = mainViewModel.deleteApplyViewModel
            val mes = context.getString(R.string.delete_alert)
            mainViewModel.nav.navigate(DeleteApplyDestination)
            vm.launchAndGet(context, message = mes) { event ->
                if (event == DeleteApplyEvent.Apply) {
                    recipeRepository.delete(state.recipe.id)
                    mainViewModel.onEvent(MainEvent.NavigateBack)
                }
            }
        }
    }

    private fun switchFavorite() {
        viewModelScope.launch {
            state.isFavorite.value = !state.recipe.inFavorite
            recipeRepository.updateRecipeFavorite(
                state.recipe,
                !state.recipe.inFavorite
            )
            update()
        }
    }

    private fun addToMenu() {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            mainViewModel.nav.navigate(SpecifySelectionDestination)
            vm.launchAndGet() { res ->
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        week.on.a.plate.screens.menu.event.ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            res.selId,
                            Position.PositionRecipeView(
                                0,
                                RecipeShortView(
                                    state.recipe.id,
                                    state.recipe.name,
                                    state.recipe.img
                                ),
                                res.portions,
                                res.selId
                            )
                        )
                    )
                }
            }
        }
    }

    private fun editRecipe() {
        viewModelScope.launch {
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

    fun update() {
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