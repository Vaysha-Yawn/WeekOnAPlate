package week.on.a.plate.screenRecipeDetails.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.navigation.ShoppingListScreen
import week.on.a.plate.data.dataView.example.emptyRecipe
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screenInventory.navigation.InventoryDirection
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()
    private val _recipeFlow: MutableStateFlow<RecipeView> = MutableStateFlow(emptyRecipe)
    val recipeFlow: StateFlow<RecipeView> = _recipeFlow

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            RecipeDetailsEvent.AddToCart -> addToCart()
            RecipeDetailsEvent.AddToMenu -> addToMenu()
            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> editRecipe()
            RecipeDetailsEvent.MinusPortionsView -> minusPortionsView()
            RecipeDetailsEvent.PlusPortionsView -> plusPortionsView()
            is RecipeDetailsEvent.StartTimerForStep -> startTimerForStep(event.time, event.act)
            RecipeDetailsEvent.SwitchFavorite -> switchFavorite()
            RecipeDetailsEvent.Delete -> delete()
        }
    }

    private fun addToCart() {
        if (state.recipe.value.ingredients.isNotEmpty() && state.recipe.value.ingredients.find { it.count>0 }!=null){
            viewModelScope.launch {
                val listCopy = state.recipe.value.ingredients.toList()
                listCopy.forEachIndexed {index,ingredientInRecipeView->
                    ingredientInRecipeView.count = state.ingredientsCounts.value[index]
                }
                mainViewModel.nav.navigate(InventoryDirection)
                mainViewModel.inventoryViewModel.launchAndGet(listCopy)
            }
        }
    }

    private fun plusPortionsView() {
        state.currentPortions.intValue = state.currentPortions.intValue.plus(1)
        updIngredientsCount()
    }

    fun updIngredientsCount() {
        val newCountPortions = state.currentPortions.intValue
        val startCount = state.recipe.value.standardPortionsCount
        if (state.recipe.value.ingredients.size == state.ingredientsCounts.value.size && state.recipe.value.ingredients.isNotEmpty()) {
            state.ingredientsCounts.value = state.ingredientsCounts.value.toMutableList().apply {
                this.forEachIndexed { index, _ ->
                    val startIngredientCount = state.recipe.value.ingredients[index].count
                    if (startIngredientCount > 0) {
                        this[index] = startIngredientCount / startCount * newCountPortions
                    }
                }
            }.toList()
        }
    }

    private fun minusPortionsView() {
        if (state.currentPortions.intValue > 1) {
            state.currentPortions.intValue = state.currentPortions.intValue.minus(1)
            updIngredientsCount()
        }
    }

    private fun delete() {
        viewModelScope.launch {
            recipeRepository.delete(state.recipe.value.id)
            mainViewModel.nav.popBackStack()
        }
    }

    private fun switchFavorite() {
        viewModelScope.launch {
            recipeRepository.updateRecipeFavorite(
                state.recipe.value,
                !state.recipe.value.inFavorite
            )
            update()
        }
    }

    private fun addToMenu() {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            mainViewModel.nav.navigate(SpecifySelectionDirection)
            vm.launchAndGet() { selId, count ->
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            selId,
                            Position.PositionRecipeView(
                                0,
                                RecipeShortView(state.recipe.value.id, state.recipe.value.name),
                                count,
                                selId
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
            vm.launchAndGet(state.recipe.value) { recipe ->
                val newRecipe = RecipeView(
                    id = 0,
                    name = recipe.name.value,
                    description = recipe.description.value,
                    img = recipe.photoLink.value,
                    tags = recipe.tags.value,
                    prepTime = recipe.prepTime.intValue,
                    allTime = recipe.allTime.intValue,
                    standardPortionsCount = recipe.portionsCount.intValue,
                    ingredients = recipe.ingredients.value,
                    steps = recipe.steps.value.map {
                        RecipeStepView(
                            0,
                            it.description.value,
                            it.image.value,
                            it.timer.intValue.toLong()
                        )
                    },
                    link = recipe.source.value, false
                )
                viewModelScope.launch {
                    recipeRepository.updateRecipe(state.recipe.value, newRecipe)
                    update()
                }
            }
        }
    }

    private fun startTimerForStep(time: Int, act: Context) {
        setTimer(act, time)
    }

    fun launch(recipeId: Long, portionsCount: Int? = null) {
        viewModelScope.launch {
            _recipeFlow.value = recipeRepository.getRecipe(recipeId)
            val list = mutableListOf<Int>()
            _recipeFlow.value.ingredients.forEach { ingredientInRecipeView ->
                val count = ingredientInRecipeView.count
                list.add(count)
            }
            state.ingredientsCounts.value = list
            state.currentPortions.intValue =
                portionsCount ?: _recipeFlow.value.standardPortionsCount
        }
    }

    fun update() {
        launch(state.recipe.value.id)
    }

}