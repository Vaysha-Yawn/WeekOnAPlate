package week.on.a.plate.core.dialogs.addTag.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.recipe.TagCategoryView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.addTag.event.AddTagEvent
import week.on.a.plate.core.dialogs.addTag.state.AddTagUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.fullScereenDialog.categoriesSearch.navigation.CategoriesSearchDestination


class AddTagViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: AddTagUIState = AddTagUIState()
    private lateinit var resultFlow: MutableStateFlow<Pair<String, TagCategoryView>?>

    fun start(): Flow<Pair<String, TagCategoryView>?> {
        val flow = MutableStateFlow<Pair<String, TagCategoryView>?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        if (state.category.value == null) return
        val result = Pair(state.text.value, state.category.value!!)
        resultFlow.value = result
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddTagEvent) {
        when (event) {
            AddTagEvent.Close -> close()
            AddTagEvent.Done -> done()
            AddTagEvent.ChooseCategory -> toSearchCategory()
        }
    }

    private fun toSearchCategory() {
        mainViewModel.viewModelScope.launch {
            val vm = mainViewModel.categoriesSearchViewModel
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(CategoriesSearchDestination)
            vm.launchAndGetTag( ) { categoryName->
                state.category.value = categoryName
                state.categoryName.value = categoryName.name
                mainViewModel.onEvent(MainEvent.ShowDialog(this@AddTagViewModel))
            }
        }
    }

    suspend fun launchAndGet(
        oldName: String?,
        oldCategory: TagCategoryView?, use: (Pair<String, TagCategoryView>) -> Unit
    ) {
       state.text.value = oldName?:""
        state.category.value = oldCategory
        state.categoryName.value = oldCategory?.name?:""
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}