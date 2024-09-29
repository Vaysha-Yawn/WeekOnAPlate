package week.on.a.plate.screenRecipeDetails.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()

    fun onEvent(event: RecipeDetailsEvent) {
        when (event) {
            RecipeDetailsEvent.AddToCart -> TODO()
            RecipeDetailsEvent.AddToMenu -> addToMenu()
            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> editRecipe()
            RecipeDetailsEvent.MinusPortionsView -> TODO()
            RecipeDetailsEvent.PlusPortionsView -> TODO()
            is RecipeDetailsEvent.StartTimerForStep -> startTimerForStep(event.time, event.act)
            RecipeDetailsEvent.SwitchFavorite -> TODO()
        }
    }

    private fun addToMenu() {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            mainViewModel.nav.navigate(SpecifySelectionDirection)
            vm.launchAndGet() {selId, count ->
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            selId,
                            Position.PositionRecipeView(
                                0,
                                RecipeShortView(0, state.recipe.value!!.name),
                                count,
                                selId
                            )
                        ), listOf()
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
                //todo edit in bd
            }
        }
    }

    private fun startTimerForStep(time: Int, act: Context) {
        setTimer(act, time)
    }

    fun launch(recipeId: Long) {
        /// todo load
        viewModelScope.launch {
            state.recipe.value = recipeTom
        }
    }


}