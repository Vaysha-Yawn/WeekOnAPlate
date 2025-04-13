package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.logic

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.dataView.example.Measure
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.event.AddIngredientEvent
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.state.AddIngredientUIState
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode

class AddIngredientViewModel(
    context: Context,
    oldIngredient: IngredientView?,
    oldCategory: IngredientCategoryView?,
    defaultCategoryView: IngredientCategoryView,
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    val mainViewModel: MainViewModel,
    useResult: (Pair<IngredientView, IngredientCategoryView>) -> Unit,
) : DialogViewModel<Pair<IngredientView, IngredientCategoryView>>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {

    val state: AddIngredientUIState = AddIngredientUIState(
        oldIngredient?.name ?: "",
        oldIngredient?.measure == context.getString(R.string.ml),
        oldCategory,
        oldIngredient?.img ?: ""
    ).apply {
        category.value = defaultCategoryView
    }

    val dialogOpenParams: MutableState<DialogOpenParams?> = mutableStateOf(null)

    fun onEvent(event: AddIngredientEvent) {
        when (event) {
            AddIngredientEvent.Close -> close()
            is AddIngredientEvent.Done -> {
                val value =
                    if (state.isLiquid.value) Measure.Milliliters.small else Measure.Grams.small
                val result = Pair(
                    IngredientView(
                        0,
                        state.photoUri.value.lowercase(),
                        state.name.value,
                        event.context.getString(value)
                    ),
                    state.category.value!!
                )
                done(result)
            }

            AddIngredientEvent.ChooseCategory -> toSearchCategory()
            AddIngredientEvent.PickImage -> pickImage()
        }
    }

    private fun pickImage() {
        mainViewModel.onEvent(MainEvent.HideDialog)
        val params =
            ChooseHowImagePickViewModel.ChooseHowImagePickDialogParams(state.photoUri.value) {
            state.photoUri.value = it
        }
        dialogOpenParams.value = params
    }

    private fun toSearchCategory() {
        mainViewModel.viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.state.selectedIngredientsCategories.value = listOf()
            vm.state.resultSearchIngredientsCategories.value = listOf()
            vm.state.searchText.value = ""

            val oldFilterState = vm.state.getCopy()
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(FilterDestination)

            vm.launchAndGet(FilterMode.One, FilterEnum.CategoryIngredient, null, true) { filters ->
                val res = filters.ingredientsCategories?.getOrNull(0)
                if (res != null) state.category.value = res
                mainViewModel.onEvent(MainEvent.ShowDialog)
                vm.isForCategory = false
                vm.state.restoreState(oldFilterState)
            }
        }
    }

    companion object {
        fun launch(
            context: Context,
            oldIngredient: IngredientView?,
            oldCategory: IngredientCategoryView?,
            defaultCategoryView: IngredientCategoryView,
            mainViewModel: MainViewModel,
            useResult: (Pair<IngredientView, IngredientCategoryView>) -> Unit
        ) {
            AddIngredientViewModel(
                context,
                oldIngredient,
                oldCategory,
                defaultCategoryView,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                mainViewModel,
                useResult
            )
        }
    }
}