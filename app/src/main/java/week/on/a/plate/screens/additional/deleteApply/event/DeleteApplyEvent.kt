package week.on.a.plate.screens.additional.deleteApply.event

import week.on.a.plate.core.Event

sealed class DeleteApplyEvent : Event() {
    data object Apply : DeleteApplyEvent()
    data object Cancel : DeleteApplyEvent()
}