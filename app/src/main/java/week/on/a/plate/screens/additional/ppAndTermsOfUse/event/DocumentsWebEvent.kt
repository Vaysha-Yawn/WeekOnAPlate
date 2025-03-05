package week.on.a.plate.screens.additional.ppAndTermsOfUse.event

import week.on.a.plate.core.Event

sealed class DocumentsWebEvent : Event() {
    data object Back : DocumentsWebEvent()
}