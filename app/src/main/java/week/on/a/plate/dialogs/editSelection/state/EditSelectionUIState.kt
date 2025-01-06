package week.on.a.plate.dialogs.editSelection.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalTime

class EditSelectionUIState(
    val text: MutableState<String> = mutableStateOf(""),
    val title: Int,
    val placeholder: Int
) {
    val selectedTime: MutableState<LocalTime> = mutableStateOf(LocalTime.of(0, 0))
}


