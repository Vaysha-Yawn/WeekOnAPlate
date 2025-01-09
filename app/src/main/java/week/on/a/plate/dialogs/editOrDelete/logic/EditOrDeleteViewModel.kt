package week.on.a.plate.dialogs.editOrDelete.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditOrDeleteViewModel (
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (EditOrDeleteEvent) -> Unit
): DialogViewModel<EditOrDeleteEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    fun onEvent(event: EditOrDeleteEvent){
        when(event) {
            EditOrDeleteEvent.Close -> close()
            else -> done(event)
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel, useResult: (EditOrDeleteEvent) -> Unit
        ) {
            EditOrDeleteViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}