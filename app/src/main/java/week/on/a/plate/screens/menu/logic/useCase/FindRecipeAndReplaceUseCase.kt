package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class FindRecipeAndReplaceUseCase @Inject constructor() {
    suspend operator fun invoke(
        positionRecipe: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit,
        onEvent: (MenuEvent) -> Unit
    ) {
        mainViewModel.nav.navigate(SearchDestination)
        mainViewModel.searchViewModel.launchAndGet(positionRecipe.selectionId, null) { recipe ->
            val recipePosition = Position.PositionRecipeView(
                0,
                RecipeShortView(recipe.id, recipe.name, recipe.img),
                2,
                positionRecipe.selectionId
            )
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        positionRecipe.selectionId,
                        recipePosition
                    )
                )
            )
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.Delete(
                        positionRecipe
                    )
                )
            )
            updateWeek()
        }
    }
}
