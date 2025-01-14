package week.on.a.plate.screens.recipeDetails.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDestination
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
                    week.on.a.plate.screens.menu.event.ActionWeekMenuDB.AddRecipePositionInMenuDB(
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