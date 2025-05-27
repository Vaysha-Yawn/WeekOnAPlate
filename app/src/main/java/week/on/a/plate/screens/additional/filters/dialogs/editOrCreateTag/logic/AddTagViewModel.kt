package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.event.AddTagEvent
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.state.AddTagUIState
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterNavParams
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode


class AddTagViewModel(
    val mainViewModel: MainViewModel,
    oldName: String?,
    oldCategory: TagCategoryView?,
    defaultCategoryView: TagCategoryView,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (Pair<String, TagCategoryView>) -> Unit
) : DialogViewModel<Pair<String, TagCategoryView>>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    val state: AddTagUIState = AddTagUIState()

    init {
        state.text.value = oldName ?: ""
        state.category.value = oldCategory ?: defaultCategoryView
    }


    fun onEvent(event: AddTagEvent) {
        when (event) {
            AddTagEvent.Close -> close()
            AddTagEvent.Done -> {
                if (state.category.value == null) return
                val result = Pair(state.text.value, state.category.value!!)
                done(result)
            }

            AddTagEvent.ChooseCategory -> toSearchCategory()
        }
    }

    private fun toSearchCategory() {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.state.selectedTagsCategories.value = listOf()
            vm.state.resultSearchTagsCategories.value = listOf()
            vm.state.searchText.value = ""

            val oldFilterState = vm.state.getCopy()
            mainViewModel.onEvent(MainEvent.HideDialog)

            val params =
                FilterNavParams(FilterMode.One, FilterEnum.CategoryTag, null, true) { filters ->
                val res = filters.tagsCategories?.getOrNull(0)
                if (res != null) state.category.value = res
                mainViewModel.onEvent(MainEvent.ShowDialog)
                vm.isForCategory = false
                vm.state.restoreState(oldFilterState)
            }
            mainViewModel.onEvent(MainEvent.Navigate(FilterDestination, params))
        }
    }

    class AddTagDialogNavParams(
        private val oldName: String?,
        private val oldCategory: TagCategoryView?,
        private val defaultCategoryView: TagCategoryView,
        private val useResult: (Pair<String, TagCategoryView>) -> Unit
    ) : DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            AddTagViewModel(
                mainViewModel,
                oldName,
                oldCategory,
                defaultCategoryView,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }

    }

}