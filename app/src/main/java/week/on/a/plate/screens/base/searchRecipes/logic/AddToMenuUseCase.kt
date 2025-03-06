package week.on.a.plate.screens.base.searchRecipes.logic

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class AddToMenuUseCase @Inject constructor(
    private val addRecipePosToDB: AddRecipePosToDBUseCase
) {
    suspend operator fun invoke(
        recipeView: RecipeView,
        context: Context,
        mainViewModel: MainViewModel,
        viewModelScope: CoroutineScope,
        close: (state: SearchUIState, mainViewModel: MainViewModel) -> Unit,
        resultFlow: MutableStateFlow<RecipeView?>?,
        state: SearchUIState
    ) {
        if (state.selId != null) {
            resultFlow?.value = recipeView
            state.selId = null
            mainViewModel.nav.navigate(MenuDestination)
        } else {
            val vmSpec = mainViewModel.specifySelectionViewModel
            close(state, mainViewModel)
            mainViewModel.nav.navigate(SpecifySelectionDestination)
            vmSpec.launchAndGet() { res ->

                val std = PreferenceUseCase().getDefaultPortionsCount(context)
                ChangePortionsCountViewModel.launch(mainViewModel, std) { count ->
                    viewModelScope.launch {
                        val recipePosition = Position.PositionRecipeView(
                            0,
                            RecipeShortView(recipeView.id, recipeView.name, recipeView.img),
                            count,
                            res.selId
                        )
                        addRecipePosToDB(recipePosition, res.selId)
                        mainViewModel.nav.navigate(MenuDestination)
                    }
                }
            }
        }
    }
}