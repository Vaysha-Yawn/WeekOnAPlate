package week.on.a.plate.menuScreen.logic.eventData

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import java.time.LocalDate

sealed class NavFromMenuData {
    class NavToFullRecipe(val rec: RecipeShortView) : NavFromMenuData()
    class SearchByDraft(val draft: Position.PositionDraftView) : NavFromMenuData()
    class FindReplaceRecipe(val recipe: Position.PositionRecipeView) : NavFromMenuData()
    class NavToAddRecipe(val selId:Long?) : NavFromMenuData()

    class DoublePositionToMenu (val position: Position) : NavFromMenuData()
    class MovePositionToMenu(val position: Position) : NavFromMenuData()

    data object NavToChooseIngredient : NavFromMenuData()
    data object NavToCreateDraft : NavFromMenuData()
}