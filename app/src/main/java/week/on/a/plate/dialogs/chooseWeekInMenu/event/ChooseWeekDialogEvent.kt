package week.on.a.plate.dialogs.chooseWeekInMenu.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed interface ChooseWeekDialogEvent : Event {
    class Done(val date: LocalDate) : ChooseWeekDialogEvent
    object Close : ChooseWeekDialogEvent
}