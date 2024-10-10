package week.on.a.plate.dialogEditSelection.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalTime

class EditSelectionUIState(startText:String = "", startTitle:String = "", startPlaceholder :String = "Введите текст здесь...") {
    val text: MutableState<String> = mutableStateOf(startText)
    val title: MutableState<String> = mutableStateOf(startTitle)
    val placeholder: MutableState<String> = mutableStateOf(startPlaceholder)
    val selectedTime: MutableState<LocalTime> = mutableStateOf(LocalTime.of(0,0))
}


