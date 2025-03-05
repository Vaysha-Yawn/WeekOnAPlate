package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddRecipePosToDBUseCase
import javax.inject.Inject

class NavToScreenForAddRecipe @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long,
        context: Context,
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        mainViewModel.nav.navigate(SearchDestination)
        mainViewModel.searchViewModel.launchAndGet(selId, null) { recipe ->
            choosePortionsCount(context, mainViewModel) { count ->
                launch(Dispatchers.IO) {
                    val recipePosition = Position.PositionRecipeView(
                        0,
                        RecipeShortView(recipe.id, recipe.name, recipe.img),
                        count,
                        selId
                    )
                    addRecipe(recipePosition, selId)
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