package week.on.a.plate.app.mainActivity.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogViewModel


sealed class MainEvent : Event {
    data object CloseDialog : MainEvent()
    class OpenDialog(val dialog: DialogViewModel<*>) : MainEvent()
    class ShowSnackBar(val message: String) : MainEvent()
    class Navigate(val destination: Any, val navParams: NavParams) : MainEvent()
    data object HideDialog : MainEvent()
    data object ShowDialog : MainEvent()
    class VoiceToText(val context:Context, val use:(ArrayList<String>?)->Unit) : MainEvent()
}