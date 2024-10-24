package week.on.a.plate.dialogs.chooseWeekInMenu.event

import week.on.a.plate.core.Event

sealed class ChooseWeekDialogEvent: Event() {
    data object Done: ChooseWeekDialogEvent()
    data object Close: ChooseWeekDialogEvent()
}