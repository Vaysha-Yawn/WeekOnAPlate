package week.on.a.plate.screens.base.menu.logic.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddRecipePosToDBUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteDraftInDBUseCase
import javax.inject.Inject

class SearchByDraftUseCase @Inject constructor(
    private val addRecipe: AddRecipePosToDBUseCase,
    private val deleteDraft: DeleteDraftInDBUseCase,
) {
    suspend operator fun invoke(
        draft: Position.PositionDraftView,
        mainViewModel: MainViewModel
    ) = coroutineScope {
        mainViewModel.searchViewModel.launchAndGet(
            draft.selectionId,
            Pair(draft.tags, draft.ingredients)
        ) { recipe ->
            launch(Dispatchers.IO) { deleteDraft(draft) }
            launch(Dispatchers.IO) {
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    2,
                    draft.selectionId
                )
                addRecipe(recipePosition, draft.selectionId)
            }
        }
        mainViewModel.nav.navigate(SearchDestination)
    }
}