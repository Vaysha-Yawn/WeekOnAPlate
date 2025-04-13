package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.coroutineScope
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.SearchNavParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe.ChoosePortionsCountOpenDialog
import javax.inject.Inject

class AddRecipeNavToScreen @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase,
    private val choosePortionsCount: ChoosePortionsCountOpenDialog
) {
    suspend operator fun invoke(
        selId: Long,
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit,
    ) = coroutineScope {
        val params = SearchNavParams(selId, null) { recipe ->
            choosePortionsCount(context, dialogOpenParams) { count ->
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    count,
                    selId
                )
                addRecipe(recipePosition, selId)
            }
        }
        onEvent(MainEvent.Navigate(SearchDestination, params))
    }
}


