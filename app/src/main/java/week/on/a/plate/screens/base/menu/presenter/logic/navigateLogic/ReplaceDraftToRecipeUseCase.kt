package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteDraftInDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetRecipeUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition.AddRecipeFinish
import javax.inject.Inject

class ReplaceDraftToRecipeUseCase @Inject constructor(
    private val addRecipe: AddRecipeFinish,
    private val deleteDraft: DeleteDraftInDBUseCase,
    private val getRecipe: GetRecipeUseCase,
) {
    suspend operator fun invoke(
        draft: Position.PositionDraftView,
        recipeId: Long, context: Context, dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        launch(Dispatchers.IO) { deleteDraft(draft) }
        launch(Dispatchers.IO) {
            val recipe = getRecipe(recipeId)
            addRecipe(
                recipe,
                draft.selectionId,
                context, dialogOpenParams
            )
        }
    }
}
