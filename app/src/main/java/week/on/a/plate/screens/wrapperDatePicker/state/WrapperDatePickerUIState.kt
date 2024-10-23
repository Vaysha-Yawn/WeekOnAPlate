package week.on.a.plate.screens.wrapperDatePicker.state

import androidx.compose.runtime.MutableState
import java.time.LocalDate

data class WrapperDatePickerUIState(
    val activeDay:MutableState<LocalDate>,
    val itsDayMenu: MutableState<Boolean>,
    val activeDayInd: MutableState<Int>,
    val titleTopBar: MutableState<String>,
    val isGroupSelectedModeActive: MutableState<Boolean>,
)