package week.on.a.plate.dialogs.editIngredientInMenu.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.state.EditPositionIngredientUIState
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode


class EditPositionIngredientViewModel(
    positionIngredient: Position.PositionIngredientView?,
    isForAdd: Boolean,
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    private val mainViewModel: MainViewModel,
    useResult: (Position.PositionIngredientView) -> Unit,
) : DialogViewModel<Position.PositionIngredientView>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    val state: EditPositionIngredientUIState = EditPositionIngredientUIState(positionIngredient)

    init {
        if (isForAdd) chooseIngredient()
    }

    fun onEvent(event: EditPositionIngredientEvent) {
        when (event) {
            EditPositionIngredientEvent.Close -> close()
            EditPositionIngredientEvent.Done -> {
                if (state.ingredientState.value!=null){
                    val newIngredientPosition = Position.PositionIngredientView(
                        state.positionIngredientView?.id ?: 0,
                        IngredientInRecipeView(
                            state.positionIngredientView?.id ?: 0,
                            state.ingredientState.value!!,
                            state.description.value,
                            state.count.intValue
                        ), state.positionIngredientView?.selectionId ?: 0
                    )
                    done(newIngredientPosition)
                }else{
                    onEvent(EditPositionIngredientEvent.Close)
                }
            }

            EditPositionIngredientEvent.ChooseIngredient -> chooseIngredient()
        }
    }

    private fun chooseIngredient() {
        viewModelScope.launch {
            mainViewModel.nav.navigate(FilterDestination)
            mainViewModel.onEvent(MainEvent.HideDialog)
            viewModelScope.launch {
                mainViewModel.filterViewModel.launchAndGet(
                    FilterMode.One, FilterEnum.Ingredient,
                    Pair(listOf(), listOf()), false
                ) {
                    mainViewModel.onEvent(MainEvent.ShowDialog)
                    val new = it.ingredients?.getOrNull(0)
                    if (new != null) state.ingredientState.value = new
                    else onEvent(EditPositionIngredientEvent.Close)
                }
            }
        }
    }

    class EditPositionIngredientDialogParams(
        private val positionIngredient: Position.PositionIngredientView?,
        private val isForAdd: Boolean,
        private val useResult: (Position.PositionIngredientView) -> Unit,
    ) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            EditPositionIngredientViewModel(
                positionIngredient,
                isForAdd,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                mainViewModel,
                useResult
            )
        }
    }
}