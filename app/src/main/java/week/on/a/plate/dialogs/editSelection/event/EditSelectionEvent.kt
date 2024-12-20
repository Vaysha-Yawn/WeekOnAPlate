package week.on.a.plate.dialogs.editSelection.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class EditSelectionEvent: Event() {
    data object Done: EditSelectionEvent()
    data object Close: EditSelectionEvent()
    data class ChooseTime(val context: Context): EditSelectionEvent()
}