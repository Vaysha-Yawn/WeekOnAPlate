package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.SearchNavParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe.ChoosePortionsCountOpenDialog
import javax.inject.Inject

class FindRecipeAndReplaceNavToScreen @Inject constructor(
    private val deleteRecipe: DeleteRecipePosInDBUseCase,
    private val addRecipe: AddRecipePosToDBUseCase,
    private val choosePortionsCount: ChoosePortionsCountOpenDialog
) {
    operator fun invoke(
        oldRecipe: Position.PositionRecipeView,
        dialogOpenParams: MutableState<DialogOpenParams?>, context: Context,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit
    ) {
        val selId = oldRecipe.selectionId
        val params = SearchNavParams(selId, null) { recipe ->
            choosePortionsCount(context, dialogOpenParams, scope) { count ->
                val newRecipeToAdd = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    count,
                    selId
                )
                addRecipe(newRecipeToAdd, selId)
            }
            scope.launch(Dispatchers.IO) {
                deleteRecipe(oldRecipe)
            }
        }
        onEvent(MainEvent.Navigate(SearchDestination, params))
    }
}
