package week.on.a.plate.screenSearchCategories.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogAddCategory.logic.AddCategoryViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenSearchCategories.event.CategoriesSearchEvent
import week.on.a.plate.screenSearchCategories.state.CategoriesSearchUIState
import javax.inject.Inject

@HiltViewModel
class CategoriesSearchViewModel @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = CategoriesSearchUIState()

    lateinit var allTagCategories: StateFlow<List<TagCategoryView>>
    lateinit var allIngredientCategories: StateFlow<List<IngredientCategoryView>>

    private lateinit var resultFlow: MutableStateFlow<Pair<CategoriesSearchEvent, TagCategoryView?>?>
    private lateinit var resultFlowIngredient: MutableStateFlow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?>
    var isTag = false

    init {
        initStartValue()
    }

    private fun initStartValue() {
        viewModelScope.launch {
                allTagCategories = recipeTagCategoryRepository.getAllTagsByCategoriesForFilters()
                    .stateIn(viewModelScope)

                allIngredientCategories =
                    ingredientCategoryRepository.getAllIngredientsByCategoriesForFilters()
                        .stateIn(viewModelScope)

        }
    }

    private fun startIngredient(): Flow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?> {
        val flow = MutableStateFlow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?>(null)
        resultFlowIngredient = flow
        return flow
    }

    suspend fun launchAndGetIngredient(use: (Pair<CategoriesSearchEvent, IngredientCategoryView?>) -> Unit) {
        isTag = false
        initStartValue()
        val flow = startIngredient()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    private fun start(): Flow<Pair<CategoriesSearchEvent, TagCategoryView?>?> {
        val flow = MutableStateFlow<Pair<CategoriesSearchEvent, TagCategoryView?>?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGetTag(use: (Pair<CategoriesSearchEvent, TagCategoryView?>) -> Unit) {
        isTag = true
        initStartValue()
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    fun onEvent(event: CategoriesSearchEvent) {
        when (event) {
            CategoriesSearchEvent.Close -> {
                done(null)
            }
            is CategoriesSearchEvent.Select -> done(event.text)
            is CategoriesSearchEvent.Create -> create(event.text)
            CategoriesSearchEvent.Search -> search()
            CategoriesSearchEvent.VoiceSearch -> voiceSearch()
        }
    }

    private fun search() {
        val listResult = if (isTag) {
            state.allTags.value.map { it.name }
                .filter { it -> it.contains(state.searchText.value, true) }
        } else {
            state.allIngredients.value.map { it.name }
                .filter { it -> it.contains(state.searchText.value, true) }
        }
        state.resultSearch.value = listResult
    }

    private fun voiceSearch() {
        mainViewModel.onEvent(MainEvent.VoiceToText() {
            state.searchText.value = it?.joinToString() ?: ""
            search()
        })
    }

    private fun create(text: String) {
        viewModelScope.launch {
            val vm = AddCategoryViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(text) { name ->
                viewModelScope.launch {
                    if (isTag) {
                        val id = recipeTagCategoryRepository.create(name)
                        val tag = recipeTagCategoryRepository.getById(id)?:return@launch
                        done(tag)
                    } else {
                        val id =ingredientCategoryRepository.create(name)
                        val ingredient = ingredientCategoryRepository.getById(id)?:return@launch
                        done(ingredient)
                    }
                }
            }
        }
    }

    fun done(tag: TagCategoryView) {
        close()
        resultFlow.value =
            Pair(CategoriesSearchEvent.Select(tag.name), tag)
    }

    fun done(ingredient: IngredientCategoryView) {
        close()
        resultFlowIngredient.value =
            Pair(CategoriesSearchEvent.Select(ingredient.name), ingredient)
    }

    fun done(text: String? = null) {
        close()
        if (text != null) {
            if (isTag) {
                val t = state.allTags.value.find { it -> it.name.contains(text, true) }
                resultFlow.value = Pair(CategoriesSearchEvent.Select(t?.name ?: ""), t)
            } else {
                val t = state.allIngredients.value.find { it -> it.name.contains(text, true) }
                resultFlowIngredient.value =
                    Pair(CategoriesSearchEvent.Select(t?.name ?: ""), t)
            }
        } else {
            if (isTag) {
                resultFlow.value = Pair(CategoriesSearchEvent.Close, null)
            } else {
                resultFlowIngredient.value = Pair(CategoriesSearchEvent.Close, null)
            }
        }
    }

    fun close() {
        if (state.resultSearch.value.isNotEmpty()) {
            state.resultSearch.value = listOf()
        } else {
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }

}