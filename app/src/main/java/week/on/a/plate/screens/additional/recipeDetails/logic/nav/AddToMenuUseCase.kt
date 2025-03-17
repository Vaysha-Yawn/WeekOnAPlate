package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import javax.inject.Inject

class AddToMenuUseCase @Inject constructor(
    private val addRecipePosToDB: AddRecipePosToDBUseCase,
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
                val recipe = Position.PositionRecipeView(
                    0,
                    RecipeShortView(
                        state.recipe.id,
                        state.recipe.name,
                        state.recipe.img
                    ),
                    res.portions,
                    res.selId
                )
                addRecipePosToDB(
                    recipe, res.selId,
                )
            }
        }
    }
}