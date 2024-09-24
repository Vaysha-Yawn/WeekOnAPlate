package week.on.a.plate.search.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.example.recipes
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.core.fullScereenDialog.specifySelection.navigation.SpecifySelection
import week.on.a.plate.core.navigation.bottomBar.MenuScreen
import week.on.a.plate.menuScreen.event.ActionWeekMenuDB
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.recipeFullScreen.navigation.RecipeDetailsDestination
import week.on.a.plate.search.event.SearchScreenEvent
import week.on.a.plate.search.state.SearchUIState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    var state = SearchUIState()
    var selId: Long? = null

    init {
        viewModelScope.launch {
            //setFromDB
            state.allTagsCategories.value = tags
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            is SearchScreenEvent -> onEvent(event)
        }
    }

    fun onEvent(event: MainEvent) {
        mainViewModel.onEvent(event)
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> search()
            SearchScreenEvent.VoiceSearch -> onEvent(MainEvent.VoiceToText() {
                state.searchText.value = it?.joinToString() ?: ""
                search()
            })

            SearchScreenEvent.Back -> close()
            is SearchScreenEvent.FlipFavorite -> flipFavorite(event.id, event.inFavorite)
            is SearchScreenEvent.AddToMenu -> addToMenu(event.recipeView)
            is SearchScreenEvent.NavigateToFullRecipe -> {
                mainViewModel.nav.navigate(RecipeDetailsDestination)
            }

            SearchScreenEvent.ToFilter -> toFilter()
            is SearchScreenEvent.SelectTag -> selectTag(event.recipeTagView)
            SearchScreenEvent.CreateRecipe -> TODO()
            SearchScreenEvent.SearchInWeb -> {
                state.isSearchInWeb.value = true
            }
            SearchScreenEvent.Clear -> searchClear()
        }
    }

    private fun searchClear() {
        state.searchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
    }

    private fun addToMenu(recipeView: RecipeView) {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            mainViewModel.nav.navigate(SpecifySelection)
            vm.launchAndGet() { selId ->
                viewModelScope.launch {
                    val recipePosition = Position.PositionRecipeView(
                        0,
                        RecipeShortView(0, recipeView.name),
                        2,
                        selId
                    )
                    sCRUDRecipeInMenu.onEvent(
                        ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            selId,
                            recipePosition
                        ), listOf()
                    )
                }
                close()
                mainViewModel.nav.navigate(MenuScreen)
            }
        }
    }

    private fun flipFavorite(id: Long, inFavorite: Boolean) {
        //todo in bd
    }

    private fun selectTag(recipeTagView: RecipeTagView) {
        state.selectedTags.value = state.selectedTags.value.toMutableList().apply {
            if (!this.contains(recipeTagView)) {
                this.add(recipeTagView)
            }
        }
    }

    private fun search() {
        viewModelScope.launch {
            //todo searchFromDB
            state.resultSearch.value =
                recipes.filter { it ->
                    it.name.contains(state.searchText.value, true)
                            && it.tags.containsAll(state.selectedTags.value)
                            && it.ingredients.map { it.ingredientView }
                        .containsAll(state.selectedIngredients.value)
                }
        }
    }

    private fun toFilter() {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(
                Pair(
                    state.selectedTags.value,
                    state.selectedIngredients.value
                )
            ) { resultFilters ->
                state.selectedTags.value = resultFilters.first
                state.selectedIngredients.value = resultFilters.second
                search()
            }
        }
    }

    fun launchAndGet(selIde: Long?, filters: Pair<List<RecipeTagView>, List<IngredientView>>?) {
        selId = selIde
        if (filters != null) {
            state.selectedTags.value = filters.first
            state.selectedIngredients.value = filters.second
        }
        search()
    }

    fun close() {
        if (state.resultSearch.value.isNotEmpty() || state.selectedTags.value.isNotEmpty() ||
            state.selectedIngredients.value.isNotEmpty() ||
            state.searchText.value != ""
        ) {
            state.resultSearch.value = listOf()
            state.selectedTags.value = listOf()
            state.selectedIngredients.value = listOf()
            state.searchText.value = ""
        } else {
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }
}