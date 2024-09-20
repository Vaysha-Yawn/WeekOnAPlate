package week.on.a.plate.fullScreenDialogs.data

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.menuScreen.data.eventData.ActionWeekMenuDB
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import java.time.LocalDate

sealed class FullScreenDialogsEvent:Event() {
    data class NavigateToMenuForCreate( val dateToLocalDate: LocalDate, val categoriesSelection: CategoriesSelection) : FullScreenDialogsEvent()
    data class ActionMenuBD(val data: ActionWeekMenuDB) : FullScreenDialogsEvent()
    data class GetSelIdAndCreate(
        val eventAfter: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : FullScreenDialogsEvent()
}