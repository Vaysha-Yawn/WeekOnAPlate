package week.on.a.plate.dialogChooseWeekInMenu.event

import week.on.a.plate.core.Event

sealed class ChooseWeekDialogEvent: Event() {
    data object Done: ChooseWeekDialogEvent()
    data object Close: ChooseWeekDialogEvent()
}