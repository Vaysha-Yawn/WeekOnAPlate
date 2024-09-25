package week.on.a.plate.screenSearchRecipes.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.example.recipes
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelection
import week.on.a.plate.core.navigation.MenuScreen
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent
import week.on.a.plate.screenSearchRecipes.state.SearchUIState
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