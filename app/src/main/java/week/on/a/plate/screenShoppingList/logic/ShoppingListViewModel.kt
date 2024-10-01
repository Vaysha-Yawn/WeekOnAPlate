package week.on.a.plate.screenShoppingList.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.screenShoppingList.event.ShoppingListEvent
import week.on.a.plate.screenShoppingList.state.ShoppingListUIState
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor() : ViewModel() {

    //todo добавить второй тип позиции - заметка для кастомных покупок

    lateinit var mainViewModel: MainViewModel
    val state = ShoppingListUIState()

    init {
        //load list from bd
        val listChecked = mutableListOf<IngredientInRecipeView>()
        val listUnchecked = mutableListOf<IngredientInRecipeView>()
        ingredients.forEach { category ->
            category.ingredientViews.forEach {
                val position = IngredientInRecipeView(0, it, "", (0..200).random())
                if ((0..1).random() == 0) {
                    listChecked.add(position)
                } else {
                    listUnchecked.add(position)
                }
            }
        }
        state.listChecked.value = listChecked
        state.listUnchecked.value = listUnchecked
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            else -> onEvent(event)
        }
    }

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            ShoppingListEvent.Add -> addIngredient()
            is ShoppingListEvent.Check -> {
                viewModelScope.launch {
                    state.listUnchecked.value = state.listUnchecked.value.toMutableList().apply {
                        this.remove(event.position)
                    }
                    state.listChecked.value = state.listChecked.value.toMutableList().apply {
                        this.add(event.position)
                    }
                }
            }
            ShoppingListEvent.DeleteChecked -> state.listChecked.value = listOf()
            is ShoppingListEvent.Uncheck -> {
                viewModelScope.launch {
                    state.listChecked.value = state.listChecked.value.toMutableList().apply {
                        this.remove(event.position)
                    }
                    state.listUnchecked.value = state.listUnchecked.value.toMutableList().apply {
                        this.add(event.position)
                    }
                }
            }
        }
    }

    private fun addIngredient() {
        viewModelScope.launch {
            val vm = EditPositionIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null) { updatedIngredient ->
                state.listUnchecked.value = state.listUnchecked.value.toMutableList().apply {
                    this.add(updatedIngredient.ingredient)
                }
            }
        }
    }
}