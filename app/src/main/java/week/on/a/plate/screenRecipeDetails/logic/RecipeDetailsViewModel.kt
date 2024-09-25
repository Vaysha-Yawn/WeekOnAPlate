package week.on.a.plate.screenRecipeDetails.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.screenRecipeDetails.util.setTimer
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor():ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = RecipeDetailsState()

    fun onEvent(event:RecipeDetailsEvent){
        when(event){
            RecipeDetailsEvent.AddToCart -> TODO()
            RecipeDetailsEvent.AddToMenu -> TODO()
            RecipeDetailsEvent.Back -> mainViewModel.nav.popBackStack()
            RecipeDetailsEvent.Edit -> TODO()
            RecipeDetailsEvent.MinusPortionsView -> TODO()
            RecipeDetailsEvent.PlusPortionsView -> TODO()
            is RecipeDetailsEvent.StartTimerForStep -> startTimerForStep(event.time, event.act)
            RecipeDetailsEvent.SwitchFavorite -> TODO()
        }
    }

    private fun startTimerForStep(time: Long, act: Context) {
        setTimer(act, time)
    }

    fun launch(recipeId:Long){
        ///
        viewModelScope.launch {
            state.recipe.value = recipeTom
        }
    }


}