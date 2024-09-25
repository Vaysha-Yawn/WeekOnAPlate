package week.on.a.plate.screenFilters.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.dialogsAddFilters.addIngrdient.logic.AddIngredientViewModel
import week.on.a.plate.dialogsAddFilters.addTag.logic.AddTagViewModel
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterUIState
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
            FilterEvent.VoiceSearchFilters -> voiceSearch()
            is FilterEvent.SelectIngredient -> selectIngredient(event.ingredient)
            is FilterEvent.SelectTag -> selectTag(event.tag)
            FilterEvent.ClearSearch -> clearSearch()
        }
    }

    private fun clearSearch() {
        state.filtersSearchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
    }

    private fun voiceSearch() {
        mainViewModel.onEvent(MainEvent.VoiceToText() { strings: ArrayList<String>? ->
            if (strings == null) return@VoiceToText
            val searchedList = strings.getOrNull(0)?.split(" ")?:return@VoiceToText
            viewModelScope.launch {
                val listIngredientView = mutableListOf<IngredientView>()
                val listTags = mutableListOf<RecipeTagView>()
                searchedList.forEach {
                    val res = searchTagOrIngredientByName(it)
                    if (res.first != null) listTags.add(res.first!!)
                    if (res.second != null) listIngredientView.add(res.second!!)
                }
                if (listIngredientView.isEmpty() && listTags.isEmpty()){
                    state.filtersSearchText.value = strings.joinToString()
                    return@launch
                }

                val vm = FilterVoiceApplyViewModel()
                vm.mainViewModel = mainViewModel
                mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                vm.launchAndGet(listTags, listIngredientView) { stateApply ->
                    stateApply.selectedTags.value.forEach { tag ->
                        if (!state.selectedTags.value.contains(tag)) {
                            state.selectedTags.value =
                                state.selectedTags.value.toMutableList().apply {
                                    this.add(tag)
                                }.toList()
                        }
                    }
                    stateApply.selectedIngredients.value.forEach { ingredient ->
                        if (!state.selectedIngredients.value.contains(ingredient)) {
                            state.selectedIngredients.value =
                                state.selectedIngredients.value.toMutableList().apply {
                                    this.add(ingredient)
                                }.toList()
                        }
                    }
                }
            }
        })
    }

    private suspend fun searchTagOrIngredientByName(name: String): Pair<RecipeTagView?, IngredientView?> {
        val tag = state.allTagsCategories.value.map { it -> it.tags }
            .find { it.find { it.tagName.contains(name, true) } != null }
            ?.find { it.tagName.contains(name, true) }
        val ingredient = state.allIngredientsCategories.value.map { it -> it.ingredientViews }
            .find { it.find { it.name.contains(name, true) } != null }
            ?.find { it.name.contains(name, true) }
        return Pair(tag, ingredient)
    }

    private fun toCreateTag() {
        viewModelScope.launch {
            val vm = AddTagViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(state.filtersSearchText.value, null) { tagData ->
                //todo add bd and refresh state
            }
        }
    }

    private fun toCreateIngredient() {
        viewModelScope.launch {
            val vm = AddIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null, null) { ingredientData ->
                //todo add bd and refresh state
            }
        }
    }

    private fun toSelectedFilters() {
        viewModelScope.launch {
            val vm = SelectedFiltersViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                state.activeFilterTabIndex.intValue,
                state.selectedTags.value,
                state.selectedIngredients.value
            ) { stateSelected ->
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
            val tagsRes = tagCategory.tags.filter { it -> it.tagName.contains(text, true) }
            resultTags.addAll(tagsRes)
        }
        state.resultSearchFilterTags.value = resultTags
    }

    private fun searchIngredients(text: String) {
        val resultIngredient = mutableListOf<IngredientView>()
        state.allIngredientsCategories.value.forEach { ingredientCategory ->
            val ingredientRes =
                ingredientCategory.ingredientViews.filter { it -> it.name.contains(text, true) }
            resultIngredient.addAll(ingredientRes)
        }
        state.resultSearchFilterIngredients.value = resultIngredient
    }

    private fun selectIngredient(ingredient: IngredientView) {
        state.selectedIngredients.value = state.selectedIngredients.value.toMutableList().apply {
            if (this.contains(ingredient)) {
                this.remove(ingredient)
            } else {
                this.add(ingredient)
            }
        }.toList()
    }

    private fun selectTag(tag: RecipeTagView) {
        state.selectedTags.value = state.selectedTags.value.toMutableList().apply {
            if (this.contains(tag)) {
                this.remove(tag)
            } else {
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