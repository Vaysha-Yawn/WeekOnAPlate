package week.on.a.plate.core.fullScereenDialog.filters.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.example.ingredients
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.dialogs.addIngrdient.logic.AddIngredientViewModel
import week.on.a.plate.core.dialogs.addTag.logic.AddTagViewModel
import week.on.a.plate.core.dialogs.filter.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.core.fullScereenDialog.filters.event.FilterEvent
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.core.fullScereenDialog.filters.state.FilterUIState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = FilterUIState()
    private lateinit var resultFlow: MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>

    init {
        //get all tags and ingredients from db
        state.allTagsCategories.value = tags
        state.allIngredientsCategories.value = ingredients
    }


    fun start(): Flow<Pair<List<RecipeTagView>, List<IngredientView>>?> {
        val flow = MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?,
        use: (Pair<List<RecipeTagView>, List<IngredientView>>) -> Unit
    ) {
        state.selectedTags.value = lastFilters?.first ?: listOf()
        state.selectedIngredients.value = lastFilters?.second ?: listOf()
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    fun onEvent(event: FilterEvent) {
        when (event) {
            FilterEvent.Back -> close()
            is FilterEvent.CreateIngredient -> toCreateIngredient()
            is FilterEvent.CreateTag -> toCreateTag()
            is FilterEvent.SearchFilter -> search(event.text)
            FilterEvent.SelectedFilters -> toSelectedFilters()
            FilterEvent.VoiceSearchFilters -> TODO()
            is FilterEvent.SelectIngredient -> selectIngredient(event.ingredient)
            is FilterEvent.SelectTag -> selectTag(event.tag)
        }
    }

    private fun toCreateTag() {
        viewModelScope.launch {
            val vm = AddTagViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(state.filtersSearchText.value, null ) { tagData->
                //todo add bd and refresh state
            }
        }
    }

    private fun toCreateIngredient() {
        viewModelScope.launch {
            val vm = AddIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null, null ) { ingredientData->
                //todo add bd and refresh state
            }
        }
    }

    private fun toSelectedFilters() {
        viewModelScope.launch {
            val vm = SelectedFiltersViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet( state.activeFilterTabIndex.intValue, state.selectedTags.value, state.selectedIngredients.value) { stateSelected->
                state.selectedTags.value = stateSelected.first
                state.selectedIngredients.value = stateSelected.second
            }
        }
    }

    private fun search(text: String) {
        viewModelScope.launch {
            searchTags(text)
            searchIngredients(text)
        }
    }

    private fun searchTags(text: String) {
        val resultTags = mutableListOf<RecipeTagView>()
        state.allTagsCategories.value.forEach { tagCategory ->
            val tagsRes = tagCategory.tags.filter { it-> it.tagName.contains(text, true) }
            resultTags.addAll(tagsRes)
        }
        state.resultSearchFilterTags.value = resultTags
    }

    private fun searchIngredients(text: String) {
        val resultIngredient = mutableListOf<IngredientView>()
        state.allIngredientsCategories.value.forEach { ingredientCategory ->
            val ingredientRes = ingredientCategory.ingredientViews.filter { it-> it.name.contains(text, true) }
            resultIngredient.addAll(ingredientRes)
        }
        state.resultSearchFilterIngredients.value = resultIngredient
    }

    private fun selectIngredient(ingredient: IngredientView) {
        state.selectedIngredients.value = state.selectedIngredients.value.toMutableList().apply {
            if (this.contains(ingredient)){
                this.remove(ingredient)
            }else{
                this.add(ingredient)
            }
        }.toList()
    }

    private fun selectTag(tag: RecipeTagView) {
        state.selectedTags.value = state.selectedTags.value.toMutableList().apply {
            if (this.contains(tag)){
                this.remove(tag)
            }else{
                this.add(tag)
            }
        }.toList()
    }


    fun close() {
        if (state.activeFilterTabIndex.intValue == 0 && state.resultSearchFilterTags.value.isNotEmpty()) {
            state.resultSearchFilterTags.value = listOf()
        } else if (state.activeFilterTabIndex.intValue == 1 && state.resultSearchFilterIngredients.value.isNotEmpty()) {
            state.resultSearchFilterIngredients.value = listOf()
        } else {
            resultFlow.value = Pair(state.selectedTags.value, state.selectedIngredients.value)

            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }

}