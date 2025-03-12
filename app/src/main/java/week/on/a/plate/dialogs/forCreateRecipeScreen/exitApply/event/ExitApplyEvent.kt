package week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.event

import week.on.a.plate.core.Event

sealed interface ExitApplyEvent : Event {
    object Exit : ExitApplyEvent
    object Close : ExitApplyEvent
}