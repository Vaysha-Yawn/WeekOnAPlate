package week.on.a.plate.screens.additional.deleteApply.event

import week.on.a.plate.core.Event

sealed interface DeleteApplyEvent : Event {
    object Apply : DeleteApplyEvent
    object Cancel : DeleteApplyEvent
}