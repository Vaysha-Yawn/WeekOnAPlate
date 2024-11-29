package week.on.a.plate.dialogs.editOrCreateTag.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.dialogs.editOrCreateTag.event.AddTagEvent
import week.on.a.plate.dialogs.editOrCreateTag.state.AddTagUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode


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
            val vm = mainViewModel.filterViewModel
            vm.state.selectedTagsCategories.value = listOf()
            vm.state.resultSearchTagsCategories.value = listOf()
            vm.state.searchText.value = ""

            val oldFilterState = vm.state.getCopy()
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(FilterDestination)

            vm.launchAndGet(FilterMode.One, FilterEnum.CategoryTag, null, true) { filters ->
                val res = filters.tagsCategories?.getOrNull(0)
                if (res != null) state.category.value = res
                mainViewModel.onEvent(MainEvent.ShowDialog)
                vm.isForCategory = false
                vm.state.restoreState(oldFilterState)
            }
        }
    }

    suspend fun launchAndGet(
        oldName: String?,
        oldCategory: TagCategoryView?,
        defaultCategoryView: TagCategoryView,
        use: suspend (Pair<String, TagCategoryView>) -> Unit
    ) {
        state.text.value = oldName ?: ""
        state.category.value = oldCategory ?: defaultCategoryView
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}