package week.on.a.plate.screens.additional.ppAndTermsOfUse.event

import week.on.a.plate.core.Event

sealed interface DocumentsWebEvent : Event {
    object Back : DocumentsWebEvent
}