package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class SearchByDraftUseCase @Inject constructor() {
    suspend operator fun invoke(
        draft: Position.PositionDraftView,
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit, onEvent: (MenuEvent) -> Unit
    ) {
        mainViewModel.searchViewModel.launchAndGet(
            draft.selectionId,
            Pair(draft.tags, draft.ingredients)
        ) { recipe ->
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(draft)))
            val recipePosition = Position.PositionRecipeView(
                0,
                RecipeShortView(recipe.id, recipe.name, recipe.img),
                2,
                draft.selectionId
            )
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        draft.selectionId,
                        recipePosition
                    )
                )
            )
            updateWeek()
        }
        mainViewModel.nav.navigate(SearchDestination)
    }
}