package week.on.a.plate.app.mainActivity.event

import android.content.Context
import android.os.Bundle
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogViewModel


sealed class MainEvent : Event {
    object CloseDialog : MainEvent()
    class OpenDialog(val dialog: DialogViewModel<*>) : MainEvent()
    class ShowSnackBar(val message: String) : MainEvent()
    object NavigateBack : MainEvent()
    class Navigate(val destination: Any) : MainEvent()
    class NavigateBackWithResult(val key: String, val result: Any) : MainEvent()
    object HideDialog : MainEvent()
    object ShowDialog : MainEvent()
    class VoiceToText(val context:Context, val use:(ArrayList<String>?)->Unit) : MainEvent()
}