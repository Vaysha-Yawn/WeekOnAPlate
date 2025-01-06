package week.on.a.plate.dialogs.editOneString.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.R

class EditOneStringUIState(startText:String = "", val title:Int = R.string.enter_text_note, val placeholder :Int = R.string.enter_text_note) {
    val text: MutableState<String> = mutableStateOf(startText)
}


