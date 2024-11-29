package week.on.a.plate.dialogs.editOrCreateIngredient.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.data.dataView.example.Measure
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.dialogs.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.dialogs.editOrCreateIngredient.event.AddIngredientEvent
import week.on.a.plate.dialogs.editOrCreateIngredient.state.AddIngredientUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode


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
        resultFlow.value = Pair(
            IngredientView(0, state.photoUri.value.lowercase(), state.name.value, if (state.isLiquid.value) Measure.Milliliters.small else Measure.Grams.small ),
            state.category.value!!
        )
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
            AddIngredientEvent.PickImage -> pickImage()
        }
    }

    private fun pickImage() {
        val vmChoose = ChooseHowImagePickViewModel()
        vmChoose.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.HideDialog)
        mainViewModel.onEvent(MainEvent.OpenDialog(vmChoose))
        mainViewModel.viewModelScope.launch {
            vmChoose.launchAndGet(state.photoUri.value){
                state.photoUri.value = it
            }
        }
    }

    private fun toSearchCategory() {
        mainViewModel.viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.state.selectedIngredientsCategories.value = listOf()
            vm.state.resultSearchIngredientsCategories.value = listOf()
            vm.state.searchText.value = ""

            val oldFilterState = vm.state.getCopy()
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(FilterDestination)

            vm.launchAndGet(FilterMode.One, FilterEnum.CategoryIngredient,null, true) { filters ->
                val res = filters.ingredientsCategories?.getOrNull(0)
                if (res != null) state.category.value = res
                mainViewModel.onEvent(MainEvent.ShowDialog)
                vm.isForCategory = false
                vm.state.restoreState(oldFilterState)
            }
        }
    }

    suspend fun launchAndGet(
        oldIngredient: IngredientView?,
        oldCategory: IngredientCategoryView?,
        defaultCategoryView: IngredientCategoryView,
        use: suspend (Pair<IngredientView, IngredientCategoryView>) -> Unit
    ) {
        state = AddIngredientUIState(
            oldIngredient?.name ?: "",
            oldIngredient?.measure == "мл",
            oldCategory,
            oldIngredient?.img ?: ""
        )

        state.category.value = defaultCategoryView

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                mainViewModel.filterViewModel.onEvent(FilterEvent.SearchFilter())
            }
        }
    }

}