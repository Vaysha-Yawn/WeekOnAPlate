package week.on.a.plate.search.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.dialogs.menu.addPosition.event.AddPositionEvent
import week.on.a.plate.core.dialogs.menu.addPosition.logic.AddPositionViewModel
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.state.CategoriesSearchUIState
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.search.event.SearchScreenEvent
import week.on.a.plate.search.state.SearchUIState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = SearchUIState()
    private lateinit var resultFlow: MutableStateFlow<RecipeView?>
    var selId:Long? = null

    init {
        viewModelScope.launch {
            //setFromDB
            state.allTagsCategories.value = tags
        }
    }

    fun onEvent(event: Event){
        when (event){
            is MainEvent -> onEvent(event)
            is SearchScreenEvent -> onEvent(event)
        }
    }

    fun onEvent(event: MainEvent){
        mainViewModel.onEvent(event)
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> TODO()
            SearchScreenEvent.VoiceSearch -> TODO()
            SearchScreenEvent.Back -> close()
            is SearchScreenEvent.FlipFavorite -> TODO()
            is SearchScreenEvent.AddToMenu -> TODO()
            is SearchScreenEvent.NavigateToFullRecipe -> TODO()
            SearchScreenEvent.ToFilter -> toFilter()
        }
    }

    private fun toFilter() {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(Pair(state.selectedTags.value, state.selectedIngredients.value)) { resultFilters->
                state.selectedTags.value = resultFilters.first
                state.selectedIngredients.value = resultFilters.second
            }
        }
    }


    fun start(): Flow<RecipeView?> {
        val flow = MutableStateFlow<RecipeView?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(selIde:Long?, use: (RecipeView) -> Unit) {
        selId = selIde
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    fun close() {
        if (state.resultSearch.value.isNotEmpty()){
            state.resultSearch.value = listOf()
        }else{
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }
}