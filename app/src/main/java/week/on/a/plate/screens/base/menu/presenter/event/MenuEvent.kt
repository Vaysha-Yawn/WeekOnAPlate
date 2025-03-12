package week.on.a.plate.screens.base.menu.presenter.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import java.time.LocalDate

sealed interface MenuEvent : Event {
    class NavigateFromMenu(val navData: MenuNavEvent) : MenuEvent
    class ActionSelect(val selectedEvent: SelectedEvent) : MenuEvent
    class ActionWrapperDatePicker(val event: WrapperDatePickerEvent) : MenuEvent
    class GetSelIdAndCreate(val context: Context) : MenuEvent
    class CreatePosition(val selId: Long, val context: Context) : MenuEvent
    class CreateFirstNonPosedPosition(
        val date: LocalDate,
        val context: Context
    ) : MenuEvent

    class EditPositionMore(val position: Position, val context: Context) : MenuEvent
    class CreateWeekSelIdAndCreatePosition(val context: Context) : MenuEvent
    class SearchByDraft(val draft: Position.PositionDraftView) : MenuEvent
    class EditOrDeleteSelection(val sel: SelectionView, val context: Context) : MenuEvent
    class CreateSelection(val date: LocalDate, val isForWeek: Boolean, val context: Context) :
        MenuEvent

    object ClearSelected : MenuEvent
    object SelectedToShopList : MenuEvent
    object DeleteSelected : MenuEvent
}