package week.on.a.plate.screenSearchRecipes.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuScreen
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenCreateRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent
import week.on.a.plate.screenSearchRecipes.state.SearchState
import week.on.a.plate.screenSearchRecipes.state.SearchUIState
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val tagCategoryRepository: RecipeTagCategoryRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    var state = SearchUIState()
    lateinit var allTagCategories: StateFlow<List<TagCategoryView>>
    private var selId: Long? = null
    private val _floAllRecipe: MutableStateFlow<List<RecipeView>> = MutableStateFlow(listOf())
    val floAllRecipe: StateFlow<List<RecipeView>> = _floAllRecipe
    var resultFlow : MutableStateFlow<RecipeView?>? = null


    init {
        viewModelScope.launch {
            allTagCategories =
                tagCategoryRepository.getAllTagsByCategoriesForFilters().stateIn(viewModelScope)

            recipeRepository.getAllRecipeFlow().collect {
                _floAllRecipe.value = it
            }
        }
    }

    private fun search() {
        searchAbstract { recipeView ->
            recipeView.name.contains(state.searchText.value.trim(), true)
                    && recipeView.tags.containsAll(state.selectedTags.value)
                    && recipeView.ingredients.map { ingredientInRecipeView -> ingredientInRecipeView.ingredientView }
                .containsAll(state.selectedIngredients.value)
        }
    }

    private fun searchAbstract(filter: (RecipeView) -> Boolean) {
        state.searched.value = SearchState.searching
        viewModelScope.launch {
            recipeRepository.getAllRecipeFlow().collect { recipeViewList ->
                _floAllRecipe.value = recipeViewList.filter { filter(it) }
                state.searched.value = SearchState.done
            }
        }

    }

    private fun searchAll() {
        searchAbstract { true }
    }

    private fun searchRandom() {
        state.searched.value = SearchState.searching
        viewModelScope.launch {
            recipeRepository.getAllRecipeFlow().collect { recipeViewList ->
                if (recipeViewList.isNotEmpty()) {
                    val mutableRecipeViewList = recipeViewList.toMutableList()
                    val listRandom = mutableListOf<RecipeView>()
                    while (listRandom.size < 20 && mutableRecipeViewList.isNotEmpty()) {
                        val random = mutableRecipeViewList.random()
                        mutableRecipeViewList.remove(random)
                        listRandom.add(random)
                    }
                    _floAllRecipe.value = listRandom
                    state.searched.value = SearchState.done
                }
            }
        }
    }

    private fun searchFavorite() {
        searchAbstract { recipeView ->
            recipeView.inFavorite
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
                search()})
            SearchScreenEvent.Back -> close()
            is SearchScreenEvent.FlipFavorite -> flipFavorite(event.recipe, event.inFavorite)
            is SearchScreenEvent.AddToMenu -> addToMenu(event.recipeView)
            is SearchScreenEvent.NavigateToFullRecipe -> navigateToFullRecipe(event.recipeView.id)
            SearchScreenEvent.ToFilter -> toFilter()
            is SearchScreenEvent.SelectTag -> selectTag(event.recipeTagView)
            is SearchScreenEvent.CreateRecipe -> createRecipe()
            SearchScreenEvent.Clear -> searchClear()
            SearchScreenEvent.SearchFavorite -> searchFavorite()
            SearchScreenEvent.SearchAll -> searchAll()
            SearchScreenEvent.SearchRandom -> searchRandom()
        }
    }

    private fun navigateToFullRecipe(id: Long) {
        mainViewModel.recipeDetailsViewModel.launch(id)
        mainViewModel.nav.navigate(RecipeDetailsDestination)
    }

    private fun createRecipe() {
        viewModelScope.launch {
            val vm = mainViewModel.recipeCreateViewModel
            mainViewModel.nav.navigate(RecipeCreateDestination)
            val listRecipe = mutableListOf<IngredientInRecipeView>()
            state.selectedIngredients.value.forEach {
                val ingredient = IngredientInRecipeView(0, it, "", 0)
                listRecipe.add(ingredient)
            }
            val recipeStart = RecipeView(
                id = 0,
                name = state.searchText.value,
                description = "",
                img = "",
                tags = state.selectedTags.value,
                prepTime = 0,
                allTime = 0,
                standardPortionsCount = 4,
                ingredients = listRecipe,
                steps = listOf(),
                link = "", false
            )
            vm.launchAndGet(recipeStart) { recipe ->
                viewModelScope.launch {
                    val newRecipe = RecipeView(
                        id = 0,
                        name = recipe.name.value,
                        description = recipe.description.value,
                        img = recipe.photoLink.value,
                        tags = recipe.tags.value,
                        prepTime = recipe.prepTime.intValue,
                        allTime = recipe.allTime.intValue,
                        standardPortionsCount = recipe.portionsCount.intValue,
                        ingredients = recipe.ingredients.value,
                        steps = recipe.steps.value.map {
                            RecipeStepView(
                                0,
                                it.description.value,
                                it.image.value,
                                it.timer.intValue.toLong()
                            )
                        },
                        link = recipe.source.value, false
                    )
                    recipeRepository.create(newRecipe)
                }
            }
        }
    }

    private fun searchClear() {
        state.searchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
        state.searched.value = SearchState.none
    }

    private fun addToMenu(recipeView: RecipeView) {
        viewModelScope.launch {
            if (selId != null) {
                resultFlow?.value = recipeView
                selId = null
                mainViewModel.nav.navigate(MenuScreen)
            } else {
                val vm = mainViewModel.specifySelectionViewModel
                mainViewModel.nav.navigate(SpecifySelectionDirection)
                vm.launchAndGet() { res ->
                    viewModelScope.launch {
                        val recipePosition = Position.PositionRecipeView(
                            0,
                            RecipeShortView(recipeView.id, recipeView.name),
                            res.portions,
                            res.selId
                        )
                        sCRUDRecipeInMenu.onEvent(
                            ActionWeekMenuDB.AddRecipePositionInMenuDB(
                                res.selId,
                                recipePosition
                            )
                        )
                    }
                    close()
                }
            }
        }
    }

    private fun flipFavorite(recipe: RecipeView, inFavorite: Boolean) {
        viewModelScope.launch {
            recipeRepository.updateRecipeFavorite(recipe, !inFavorite)
        }
    }

    private fun selectTag(recipeTagView: RecipeTagView) {
        state.selectedTags.value = state.selectedTags.value.toMutableList().apply {
            if (!this.contains(recipeTagView)) {
                this.add(recipeTagView)
            }
        }
    }

    private fun toFilter() {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(
                FilterMode.All,
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

    fun start(): MutableStateFlow<RecipeView?> {
        val flow = MutableStateFlow<RecipeView?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet( selIde: Long?, filters: Pair<List<RecipeTagView>, List<IngredientView>>?,  use: suspend (RecipeView) -> Unit) {
        selId = selIde
        if (filters != null) {
            state.selectedTags.value = filters.first
            state.selectedIngredients.value = filters.second
        }
        search()
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                resultFlow = null
                return@collect
            }
        }
    }

    fun close() {
        if (state.resultSearch.value.isNotEmpty() || state.selectedTags.value.isNotEmpty() ||
            state.selectedIngredients.value.isNotEmpty() ||
            state.searchText.value != "" || state.searched.value == SearchState.done
        ) {
            state.resultSearch = mutableStateOf(listOf())
            state.selectedTags.value = listOf()
            state.selectedIngredients.value = listOf()
            state.searchText.value = ""
            state.searched.value = SearchState.none
        } else {
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }
}