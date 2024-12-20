package week.on.a.plate.screens.documentsWeb.event

import week.on.a.plate.core.Event

sealed class DocumentsWebEvent : Event() {
    data object Back : DocumentsWebEvent()
}