package week.on.a.plate.recipeFullScreen.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.example.recipeTom
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
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
            is RecipeDetailsEvent.StartTimerForStep -> TODO()
            RecipeDetailsEvent.SwitchFavorite -> TODO()
        }
    }

    fun launch(recipeId:Long){
        ///
        viewModelScope.launch {
            state.recipe.value = recipeTom
        }
    }


}