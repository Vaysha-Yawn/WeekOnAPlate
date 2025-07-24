package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import android.content.Context
import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.recipe.ChoosePortionsCountOpenDialog
import javax.inject.Inject

class AddRecipeFinish @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase,
    private val choosePortionsCount: ChoosePortionsCountOpenDialog
) {
    operator fun invoke(
        recipe: RecipeView,
        selId: Long,
        startPortions: Int,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) {
        choosePortionsCount(startPortions, dialogOpenParams) { count ->
            val recipePosition = Position.PositionRecipeView(
                0,
                RecipeShortView(recipe.id, recipe.name, recipe.img),
                count,
                selId
            )
            addRecipe(recipePosition, selId)
        }
    }

    operator fun invoke(
        recipe: RecipeView,
        selId: Long,
        context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) {
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
}


