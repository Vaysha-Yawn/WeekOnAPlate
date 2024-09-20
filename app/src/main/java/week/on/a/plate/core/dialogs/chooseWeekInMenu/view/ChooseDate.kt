package week.on.a.plate.core.dialogs.chooseWeekInMenu.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import week.on.a.plate.core.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.fullScreenDialogs.view.DatePickerMy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDate(
    state: ChooseWeekUIState,
    onClose: () -> Unit,
    done: () -> Unit
) {
    DatePickerMy(
        state.state,
        state.show, onClose,
        done
    )
}