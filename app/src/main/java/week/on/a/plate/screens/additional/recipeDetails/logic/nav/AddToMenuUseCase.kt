package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionDestination
import week.on.a.plate.screens.additional.specifySelection.navigation.SpecifySelectionParams
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import javax.inject.Inject

class AddToMenuUseCase @Inject constructor(
    private val addRecipePosToDB: AddRecipePosToDBUseCase,
) {
    suspend operator fun invoke(
        state: RecipeDetailsState, scope: CoroutineScope, onEvent: (MainEvent) -> Unit,
    ) = coroutineScope {
        val params = SpecifySelectionParams { res ->
            scope.launch(Dispatchers.IO) {
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
        onEvent(MainEvent.Navigate(SpecifySelectionDestination, params))
    }
}