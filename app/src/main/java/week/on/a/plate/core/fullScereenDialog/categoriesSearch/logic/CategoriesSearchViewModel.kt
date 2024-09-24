package week.on.a.plate.core.fullScereenDialog.categoriesSearch.logic

import androidx.compose.animation.fadeIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogs.addCategory.logic.AddCategoryViewModel
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.event.CategoriesSearchEvent
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.state.CategoriesSearchUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.example.ingredients
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.TagCategoryView
import javax.inject.Inject

@HiltViewModel
class CategoriesSearchViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = CategoriesSearchUIState()
    private lateinit var resultFlow: MutableStateFlow<Pair<CategoriesSearchEvent, TagCategoryView?>?>
    private lateinit var resultFlowIngredient: MutableStateFlow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?>
    private var isTag = false

    private fun startIngredient(): Flow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?> {
        val flow = MutableStateFlow<Pair<CategoriesSearchEvent, IngredientCategoryView?>?>(null)
        resultFlowIngredient = flow
        return flow
    }

    suspend fun launchAndGetIngredient(use: (Pair<CategoriesSearchEvent, IngredientCategoryView?>) -> Unit) {
        isTag = false
        state.allNames.value = ingredients.map { it->it.name }
        val flow = startIngredient()
        flow.collect { value ->
            if (value != null){
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
        state.allNames.value = tags.map { it->it.name }
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
        val listResult = state.allNames.value.filter { it->it.contains(state.searchText.value, true) }
        state.resultSearch.value = listResult
    }

    private fun voiceSearch() {
        mainViewModel.onEvent(MainEvent.VoiceToText(){
            state.searchText.value = it?.joinToString()?:""
            search()
        })
    }

    private fun create(text: String) {
        viewModelScope.launch {
            val vm =  AddCategoryViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(text) { name->
                if (isTag){}
                //todo create category
                // and add
            }
        }
    }

    fun done(text: String?) {
        close()
        if (text!=null) {
            if (isTag) {
                val tag = tags.find { it -> it.name.contains(text, true) }
                resultFlow.value = Pair(CategoriesSearchEvent.Select(tag!!.name), tag)
            } else {
                val ingredient = ingredients.find { it -> it.name.contains(text, true) }
                resultFlowIngredient.value = Pair(CategoriesSearchEvent.Select(ingredient!!.name), ingredient)
            }
        }else{
            if (isTag) {
                resultFlow.value = Pair(CategoriesSearchEvent.Close, null)
            }else{
                resultFlowIngredient.value = Pair(CategoriesSearchEvent.Close, null)
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