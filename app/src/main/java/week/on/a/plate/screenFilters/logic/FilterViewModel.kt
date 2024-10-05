package week.on.a.plate.screenFilters.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogAddIngredient.logic.AddIngredientViewModel
import week.on.a.plate.dialogAddTag.logic.AddTagViewModel
import week.on.a.plate.dialogEditOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogEditOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenDeleteApply.event.DeleteApplyEvent
import week.on.a.plate.screenDeleteApply.logic.DeleteApplyViewModel
import week.on.a.plate.screenDeleteApply.navigation.DeleteApplyDirection
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.screenFilters.state.FilterUIState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeTagRepository: RecipeTagRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = FilterUIState()
    lateinit var allIngredients: StateFlow<List<IngredientCategoryView>>
    lateinit var allTags: StateFlow<List<TagCategoryView>>
    private lateinit var resultFlow: MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>

    init {
        viewModelScope.launch {
            allIngredients = ingredientCategoryRepository.getAllIngredientsByCategoriesForFilters()
                .stateIn(viewModelScope)
            allTags = recipeTagCategoryRepository.getAllTagsByCategoriesForFilters()
                .stateIn(viewModelScope)
        }
    }

    fun start(): Flow<Pair<List<RecipeTagView>, List<IngredientView>>?> {
        val flow = MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        mode: FilterMode,
        lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?,
        use: (Pair<List<RecipeTagView>, List<IngredientView>>) -> Unit
    ) {
        state.selectedTags.value = lastFilters?.first ?: listOf()
        state.selectedIngredients.value = lastFilters?.second ?: listOf()
        state.filterMode.value = mode
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
            FilterEvent.Done -> done()
            is FilterEvent.EditOrDeleteIngredient -> editOrDeleteIngredient(event.ingredient)
            is FilterEvent.EditOrDeleteTag -> editOrDeleteTag(event.tag)
        }
    }

    private fun editOrDeleteTag(tag: RecipeTagView) {
        val vm = EditOrDeleteViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet { event ->
                when (event) {
                    EditOrDeleteEvent.Close -> {}
                    EditOrDeleteEvent.Delete -> deleteTag(tag)
                    EditOrDeleteEvent.Edit -> editTag(tag)
                }
            }
        }
    }

    private fun deleteIngredient(ingredient: IngredientView) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            //todo аписать предупреждение
            mainViewModel.nav.navigate(DeleteApplyDirection)
            vmDel.launchAndGet("") { event ->
                if (event == DeleteApplyEvent.Apply) {
                    ingredientRepository.delete(ingredient.ingredientId)
                }
            }
        }
    }

    private fun deleteTag(tag: RecipeTagView) {
        viewModelScope.launch {
            val vmDel = mainViewModel.deleteApplyViewModel
            //todo написать предупреждение
            mainViewModel.nav.navigate(DeleteApplyDirection)
            vmDel.launchAndGet("") { event ->
                if (event == DeleteApplyEvent.Apply) {
                    recipeTagRepository.delete(tag.id)
                }
            }
        }
    }

    private fun editOrDeleteIngredient(ingredient: IngredientView) {
        val vm = EditOrDeleteViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet { event ->
                when (event) {
                    EditOrDeleteEvent.Close -> {}
                    EditOrDeleteEvent.Delete -> deleteIngredient(ingredient)
                    EditOrDeleteEvent.Edit -> editIngredient(ingredient)
                }
            }
        }
    }

    private suspend fun editTag(tag: RecipeTagView) {
        val vm = AddTagViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        val oldCategory = allTags.value.find { it.tags.contains(tag) }
        viewModelScope.launch {
            vm.launchAndGet(tag.tagName, oldCategory) { newNameAndCategory ->
                viewModelScope.launch {
                    editTagDB(tag, newNameAndCategory.first, newNameAndCategory.second)
                }
            }
        }
    }

    private suspend fun editTagDB(
        oldTag: RecipeTagView,
        newName: String,
        newCategory: TagCategoryView
    ) {
        recipeTagRepository.update(oldTag.id, newName, newCategory.id)
    }

    private suspend fun editIngredient(ingredient: IngredientView) {
        val vm = AddIngredientViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        val oldCategory = allIngredients.value.find { it.ingredientViews.contains(ingredient) }
        viewModelScope.launch {
            vm.launchAndGet(ingredient, oldCategory) { newIngredientAndCategory ->
                viewModelScope.launch {
                    editIngredientDB(
                        ingredient,
                        newIngredientAndCategory.first,
                        newIngredientAndCategory.second
                    )
                }
            }
        }
    }

    private suspend fun editIngredientDB(
        ingredientOld: IngredientView,
        ingredientNew: IngredientView,
        newCategory: IngredientCategoryView
    ) {
        ingredientRepository.update(
            ingredientOld.ingredientId,
            newCategory.id,
            ingredientNew.img,
            ingredientNew.name,
            ingredientNew.measure
        )
    }

    private fun clearSearch() {
        state.filtersSearchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
    }

    private fun voiceSearch() {
        mainViewModel.onEvent(MainEvent.VoiceToText() { strings: ArrayList<String>? ->
            if (strings == null) return@VoiceToText
            val searchedList = strings.getOrNull(0)?.split(" ") ?: return@VoiceToText
            viewModelScope.launch {
                val listIngredientView = mutableListOf<IngredientView>()
                val listTags = mutableListOf<RecipeTagView>()
                searchedList.forEach {
                    val res = searchTagOrIngredientByName(it)
                    if (res.first != null) listTags.add(res.first!!)
                    if (res.second != null) listIngredientView.add(res.second!!)
                }
                if (listIngredientView.isEmpty() && listTags.isEmpty()) {
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
                viewModelScope.launch {
                    val newTagId = recipeTagRepository.insert(tagData.first, tagData.second.id)
                    val newTag = recipeTagRepository.getById(newTagId)
                    if (newTag != null) onEvent(FilterEvent.SelectTag(newTag))
                }
            }
        }
    }

    private fun toCreateIngredient() {
        viewModelScope.launch {
            val vm = AddIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            val oldIngredient = IngredientView(
                0,
                img = "",
                name = state.filtersSearchText.value,
                measure = ""
            )
            vm.launchAndGet(oldIngredient, null) { ingredientData ->
                viewModelScope.launch {
                    val newIngredientId = ingredientRepository.insert(
                        categoryId = ingredientData.second.id,
                        img = ingredientData.first.img,
                        name = ingredientData.first.name,
                        measure = ingredientData.first.measure
                    )
                    val newIngredient = ingredientRepository.getById(newIngredientId)
                    if (newIngredient != null) onEvent(FilterEvent.SelectIngredient(newIngredient))
                }
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
            if (state.filterMode.value == FilterMode.OneIngredient) {
                this.clear()
                this.add(ingredient)
            } else {
                if (this.contains(ingredient)) {
                    this.remove(ingredient)
                } else {
                    this.add(ingredient)
                }
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


    fun done() {
        resultFlow.value = Pair(state.selectedTags.value, state.selectedIngredients.value)
        mainViewModel.onEvent(MainEvent.NavigateBack)
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