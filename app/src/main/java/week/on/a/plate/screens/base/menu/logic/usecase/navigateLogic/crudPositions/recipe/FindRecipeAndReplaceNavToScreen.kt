package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteRecipePosInDBUseCase
import javax.inject.Inject

class FindRecipeAndReplaceNavToScreen @Inject constructor(
    private val deleteRecipe: DeleteRecipePosInDBUseCase,
    private val addRecipe: AddRecipePosToDBUseCase,
    private val choosePortionsCount: ChoosePortionsCountOpenDialog
) {
    suspend operator fun invoke(
        oldRecipe: Position.PositionRecipeView,
        mainViewModel: MainViewModel, context: Context,
    ) = coroutineScope {
        mainViewModel.nav.navigate(SearchDestination)
        val selId = oldRecipe.selectionId
        mainViewModel.searchViewModel.launchAndGet(selId, null) { recipe ->
            choosePortionsCount(context, mainViewModel) { count ->
                val newRecipeToAdd = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    count,
                    selId
                )
                addRecipe(newRecipeToAdd, selId)
            }
            launch(Dispatchers.IO) {
                deleteRecipe(oldRecipe)
            }
        }
    }
}
