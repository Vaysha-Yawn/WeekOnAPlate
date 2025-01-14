package week.on.a.plate.screens.menu.logic.useCase.crudPositions.recipe

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.preference.PreferenceUseCase
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class CreateRecipe @Inject constructor() {

    suspend operator fun invoke(
        selId: Long,
        context: Context,
        scope: CoroutineScope,
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit,
        onEvent: (MenuEvent) -> Unit
    ) {
        mainViewModel.nav.navigate(SearchDestination)
        mainViewModel.searchViewModel.launchAndGet(selId, null) { recipe ->
            choosePortionsCount(context, mainViewModel) { count ->
                scope.launch {
                    val recipePosition = Position.PositionRecipeView(
                        0,
                        RecipeShortView(recipe.id, recipe.name, recipe.img),
                        count,
                        selId
                    )
                    onEvent( MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.AddRecipePositionInMenuDB(
                            selId,
                            recipePosition
                        )))
                    updateWeek()
                }
            }
        }
    }

    private fun choosePortionsCount(
        context: Context,
        mainViewModel: MainViewModel,
        use: (Int) -> Unit
    ) {
        val std = PreferenceUseCase().getDefaultPortionsCount(context)
        ChangePortionsCountViewModel.launch(mainViewModel, std) { count ->
            use(count)
        }
    }
}