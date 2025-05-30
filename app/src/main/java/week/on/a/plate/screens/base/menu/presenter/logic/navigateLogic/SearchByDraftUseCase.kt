package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.core.navigation.SearchNavParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteDraftInDBUseCase
import javax.inject.Inject

class SearchByDraftUseCase @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase,
    private val deleteDraft: DeleteDraftInDBUseCase,
) {
    suspend operator fun invoke(
        draft: Position.PositionDraftView,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit
    ) = coroutineScope {
        val params =
            SearchNavParams(draft.selectionId, Pair(draft.tags, draft.ingredients)) { recipe ->
                scope.launch(Dispatchers.IO) { deleteDraft(draft) }
                scope.launch(Dispatchers.IO) {
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    2,
                    draft.selectionId
                )
                addRecipe(recipePosition, draft.selectionId)
            }
        }
        onEvent(MainEvent.Navigate(SearchDestination, params))
    }
}