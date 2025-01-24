package week.on.a.plate.dialogs.chooseWeekInMenu.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class ChooseWeekDialogEvent: Event() {
    data class Done(val date:LocalDate): ChooseWeekDialogEvent()
    data object Close: ChooseWeekDialogEvent()
}