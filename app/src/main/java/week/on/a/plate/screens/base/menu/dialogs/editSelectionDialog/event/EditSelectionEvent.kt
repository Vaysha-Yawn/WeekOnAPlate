package week.on.a.plate.screens.base.menu.dialogs.editSelectionDialog.event

import android.content.Context
import week.on.a.plate.core.Event

sealed interface EditSelectionEvent : Event {
    object Done : EditSelectionEvent
    object Close : EditSelectionEvent
    class ChooseTime(val context: Context) : EditSelectionEvent
}