package week.on.a.plate.screens.base.searchRecipes.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tagCategoryRepository: RecipeTagCategoryRepository,
    private val recipeRepository: RecipeRepository,
    private val addToMenu: AddToMenuUseCase,
    private val createRecipe: CreateRecipeUseCase,
    private val filtersMore: FiltersMoreUseCase,
    private val flipFavorite: FlipFavoriteUseCase,
    private val openFilters: OpenFiltersUseCase,
    private val searchManager: SearchManager,
    private val searchStateManager: SearchStateManager,
    private val selectTags: SelectTagsUseCase,
    private val sortingManager: SortingManager
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    var state = SearchUIState()
    lateinit var allTagCategories: StateFlow<List<TagCategoryView>>

    private lateinit var floAllRecipe: StateFlow<List<RecipeView>>
    private var resultFlow: MutableStateFlow<RecipeView?>? = null

    init {
        viewModelScope.launch {
            allTagCategories =
                tagCategoryRepository.getAllTagsByCategoriesForFilters().stateIn(viewModelScope)
            floAllRecipe = recipeRepository.getAllRecipeFlow().stateIn(viewModelScope)
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            is SearchScreenEvent -> onEvent(event)
        }
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> searchManager.search(state, viewModelScope, floAllRecipe)
            is SearchScreenEvent.VoiceSearch -> onEvent(MainEvent.VoiceToText(event.context) {
                state.searchText.value = it?.joinToString() ?: ""
                onEvent(SearchScreenEvent.Search)
            })

            SearchScreenEvent.Back -> searchStateManager.close(state, mainViewModel)
            is SearchScreenEvent.FlipFavorite -> viewModelScope.launch{
                flipFavorite(event.recipe, event.inFavorite)
            }
            is SearchScreenEvent.AddToMenu -> viewModelScope.launch{
                addToMenu(event.recipeView, event.context, mainViewModel, viewModelScope, searchStateManager::close, resultFlow, state)
            }
            is SearchScreenEvent.NavigateToFullRecipe -> navigateToFullRecipe(event.recipeView.id)
            SearchScreenEvent.ToFilter -> viewModelScope.launch{
                openFilters(mainViewModel, state){
                    onEvent(SearchScreenEvent.Search)
                }
            }
            is SearchScreenEvent.SelectTag -> selectTags(event.recipeTagView, state)
            is SearchScreenEvent.CreateRecipe -> viewModelScope.launch{
                createRecipe(mainViewModel, state, viewModelScope)
            }
            SearchScreenEvent.Clear -> searchStateManager.searchClear(state)
            SearchScreenEvent.SearchFavorite -> searchManager.searchFavorite(state, viewModelScope, floAllRecipe)
            SearchScreenEvent.SearchAll -> searchManager.searchAll(state, viewModelScope, floAllRecipe)
            SearchScreenEvent.SearchRandom -> searchManager.searchRandom(state, viewModelScope, floAllRecipe)
            is SearchScreenEvent.ChangeSort -> sortingManager.changeSort(event.type, event.direction, state)
            SearchScreenEvent.FiltersMore -> filtersMore(mainViewModel, state){
                onEvent(SearchScreenEvent.Search)
            }
            SearchScreenEvent.SavePreset -> TODO()
            SearchScreenEvent.SortMore -> sortingManager.sortMore(mainViewModel, ::onEvent)
        }
    }

    private fun navigateToFullRecipe(id: Long) {
        mainViewModel.recipeDetailsViewModel.launch(id)
    }

    fun start(): MutableStateFlow<RecipeView?> {
        val flow = MutableStateFlow<RecipeView?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        selIde: Long?,
        filters: Pair<List<RecipeTagView>, List<IngredientView>>?,
        use: suspend (RecipeView) -> Unit
    ) {
        state.selId = selIde
        if (filters != null) {
            state.selectedTags.value = filters.first
            state.selectedIngredients.value = filters.second
        }
        onEvent(SearchScreenEvent.Search)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                resultFlow = null
                return@collect
            }
        }
    }


}