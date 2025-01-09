package week.on.a.plate.mainActivity.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.DialogViewModel


sealed class MainEvent : Event() {
    data object CloseDialog : MainEvent()
    class OpenDialog(val dialog: DialogViewModel<*>) : MainEvent()
    class ShowSnackBar(val message: String) : MainEvent()
    class Navigate(val destination: Any) : MainEvent()
    data object NavigateBack : MainEvent()
    data object HideDialog : MainEvent()
    data object ShowDialog : MainEvent()
    data object UseSharedLink : MainEvent()
    class VoiceToText(val context:Context, val use:(ArrayList<String>?)->Unit) : MainEvent()
}