package week.on.a.plate.screens.additional.recipeDetails.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.base.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.CRUDRecipeInMenu
import javax.inject.Inject

class AddToMenuUseCase @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) {
    suspend operator fun invoke(
        mainViewModel: MainViewModel,
        viewModelScope: CoroutineScope,
        state: RecipeDetailsState,
    ) {
        val vm = mainViewModel.specifySelectionViewModel
        mainViewModel.nav.navigate(SpecifySelectionDestination)
        vm.launchAndGet() { res ->
            viewModelScope.launch {
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        res.selId,
                        Position.PositionRecipeView(
                            0,
                            RecipeShortView(
                                state.recipe.id,
                                state.recipe.name,
                                state.recipe.img
                            ),
                            res.portions,
                            res.selId
                        )
                    )
                )
            }
        }
    }
}