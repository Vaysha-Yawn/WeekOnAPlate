package week.on.a.plate.dialogs.exitApply.event

import week.on.a.plate.core.Event

sealed class ExitApplyEvent: Event() {
    data object Exit: ExitApplyEvent()
    data object Close: ExitApplyEvent()
}