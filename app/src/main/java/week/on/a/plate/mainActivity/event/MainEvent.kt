package week.on.a.plate.mainActivity.event

import androidx.navigation.NavHostController
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.DialogViewModel


sealed class MainEvent : Event() {
    data object CloseDialog : MainEvent()
    class OpenDialog(val dialog: DialogViewModel) : MainEvent()
    class ActionDBMenu(val actionMenuDBData: week.on.a.plate.screens.menu.event.ActionWeekMenuDB) : MainEvent()
    class ShowSnackBar(val message: String) : MainEvent()
    class Navigate(val destination: Any) : MainEvent()
    data object NavigateBack : MainEvent()
    data object HideDialog : MainEvent()
    class ShowDialog(val dialog: DialogViewModel) : MainEvent()
    class UseSharedLink(val link: String) : MainEvent()
    class VoiceToText(val use:(ArrayList<String>?)->Unit) : MainEvent()
}




