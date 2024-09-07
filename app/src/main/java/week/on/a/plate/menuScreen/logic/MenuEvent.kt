package week.on.a.plate.menuScreen.logic

import week.on.a.plate.core.data.week.RecipeShortView
import java.time.LocalDate

sealed class MenuEvent {
    data object SwitchWeekOrDayView : MenuEvent()
    class AddCheckState(val id: Long) : MenuEvent()
    class NavToFullRecipe(val rec: RecipeShortView) : MenuEvent()
    data object SwitchEditMode : MenuEvent()
    class CheckRecipe(val id: Long) : MenuEvent()
    class AddRecipeToCategory(val date: LocalDate, val category: String) : MenuEvent()
    class RecipeToNextStep(val id: Long) : MenuEvent()
    class Edit(val id: Long) : MenuEvent()
    data object ChooseAll : MenuEvent()
    data object DeleteSelected : MenuEvent()
    data object SelectedToShopList : MenuEvent()
}