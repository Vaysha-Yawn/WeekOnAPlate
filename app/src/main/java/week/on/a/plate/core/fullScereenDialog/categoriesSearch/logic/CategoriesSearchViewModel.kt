package week.on.a.plate.core.fullScereenDialog.categoriesSearch.logic

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
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.TagCategoryView
import javax.inject.Inject

@HiltViewModel
class CategoriesSearchViewModel @Inject constructor() : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = CategoriesSearchUIState()
    private lateinit var resultFlow: MutableStateFlow<TagCategoryView?>

    init {
        //searchFromBd
        state.allNames.value = tags.map { it->it.name }
    }

    fun start(): Flow<TagCategoryView?> {
        val flow = MutableStateFlow<TagCategoryView?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: (TagCategoryView) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

    fun onEvent(event: CategoriesSearchEvent) {
        when (event) {
            CategoriesSearchEvent.Close -> close()
            is CategoriesSearchEvent.Select -> done(event.text)
            is CategoriesSearchEvent.Create -> create(event.text)
            CategoriesSearchEvent.Search -> search()
            CategoriesSearchEvent.VoiceSearch -> voiceSearch()
        }
    }

    private fun search() {
        val listResult = state.allNames.value.filter { it->it.contains(state.searchText.value) }
        state.resultSearch.value = listResult
    }

    private fun voiceSearch() {
        //todo
    }

    private fun create(text: String) {
        viewModelScope.launch {
            val vm =  AddCategoryViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(text) { name->
                //todo create category
                // and add
            }
        }
    }

    fun done(text: String) {
        close()
        resultFlow.value = tags.find { it->it.name == text }
    }

    fun close() {
        if (state.resultSearch.value.isNotEmpty()){
            state.resultSearch.value = listOf()
        }else{
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }

}