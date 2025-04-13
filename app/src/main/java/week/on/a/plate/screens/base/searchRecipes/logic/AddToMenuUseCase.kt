package week.on.a.plate.screens.base.searchRecipes.logic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.EmptyNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionParams
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class AddToMenuUseCase @Inject constructor(
    private val addRecipePosToDB: AddRecipePosToDBUseCase
) {
    suspend operator fun invoke(
        recipeView: RecipeView,
        context: Context,
        close: (state: SearchUIState, onEvent: (Event) -> Unit) -> Unit,
        resultFlow: MutableStateFlow<RecipeView?>?,
        state: SearchUIState,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        if (state.selId != null) {
            resultFlow?.value = recipeView
            state.selId = null
            onEvent(MainEvent.Navigate(MenuDestination, EmptyNavParams))
        } else {
            close(state, onEvent)
            val params = SpecifySelectionParams { res ->
                val std = PreferenceUseCase.getDefaultPortionsCount(context)
                val params =
                    ChangePortionsCountViewModel.ChangePortionsCountDialogParams(std) { count ->
                        launch {
                        val recipePosition = Position.PositionRecipeView(
                            0,
                            RecipeShortView(recipeView.id, recipeView.name, recipeView.img),
                            count,
                            res.selId
                        )
                        addRecipePosToDB(recipePosition, res.selId)
                            onEvent(MainEvent.Navigate(MenuDestination, EmptyNavParams))
                    }
                }
                dialogOpenParams.value = params
            }
            onEvent(MainEvent.Navigate(SpecifySelectionDestination, params))
        }
    }
}