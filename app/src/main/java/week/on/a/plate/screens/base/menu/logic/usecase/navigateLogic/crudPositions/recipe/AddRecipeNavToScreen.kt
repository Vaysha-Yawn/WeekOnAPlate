package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe

import android.content.Context
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddRecipePosToDBUseCase
import javax.inject.Inject

class AddRecipeNavToScreen @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase,
    private val choosePortionsCount: ChoosePortionsCountOpenDialog
) {
    suspend operator fun invoke(
        selId: Long,
        context: Context,
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        mainViewModel.nav.navigate(SearchDestination)
        mainViewModel.searchViewModel.launchAndGet(selId, null) { recipe ->
            choosePortionsCount(context, mainViewModel) { count ->
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


