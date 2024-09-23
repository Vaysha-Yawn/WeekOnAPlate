package week.on.a.plate.core.dialogs.addIngrdient.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.core.dialogs.addIngrdient.state.AddIngredientUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.navigation.CategoriesSearchDestination


class AddIngredientViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: AddIngredientUIState
    private lateinit var resultFlow: MutableStateFlow<Pair<IngredientView, IngredientCategoryView>?>

    fun start(): Flow<Pair<IngredientView, IngredientCategoryView>?> {
        val flow = MutableStateFlow<Pair<IngredientView, IngredientCategoryView>?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = Pair(IngredientView(0, state.photoUri.value, state.name.value, state.measure.value), state.category.value!!)
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddIngredientEvent) {
        when (event) {
            AddIngredientEvent.Close -> close()
            AddIngredientEvent.Done -> done()
            AddIngredientEvent.ChooseCategory -> toSearchCategory()
        }
    }

    private fun toSearchCategory() {
        mainViewModel.viewModelScope.launch {
            val vm = mainViewModel.categoriesSearchViewModel
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(CategoriesSearchDestination)
            vm.launchAndGetIngredient( ) { categoryName->
                state.category.value = categoryName
                mainViewModel.onEvent(MainEvent.ShowDialog(this@AddIngredientViewModel))
            }
        }
    }

    suspend fun launchAndGet(oldIngredient:IngredientView?,oldCategory:IngredientCategoryView?, use: (Pair<IngredientView, IngredientCategoryView>) -> Unit) {
        state = AddIngredientUIState(oldIngredient?.name?:"", oldIngredient?.measure?:"", oldCategory, oldIngredient?.img?:"")

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}