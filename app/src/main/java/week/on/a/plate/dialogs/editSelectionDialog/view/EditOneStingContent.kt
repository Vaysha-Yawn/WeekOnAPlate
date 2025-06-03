package week.on.a.plate.dialogs.editSelectionDialog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.editSelectionDialog.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelectionDialog.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelectionDialog.state.EditSelectionUIState
import java.time.format.DateTimeFormatter

@Composable
fun EditSelectionContent(
    vm: EditSelectionViewModel,
    mainVM: MainViewModel
) {
    val state = vm.state
    val onEvent = { event: EditSelectionEvent ->
        vm.onEvent(event)
    }
    EditSelectionView(state, onEvent)
    MainEventResolve(vm.mainEvent, vm.dialogOpenParams, mainVM)
}

@Composable
private fun EditSelectionView(
    state: EditSelectionUIState,
    onEvent: (EditSelectionEvent) -> Unit,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        TextTitle(text = stringResource(state.title))
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            state.text,
            stringResource(state.placeholder), modifier = Modifier.focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextTitle(text = stringResource(R.string.choose_meal_times))
        TextSmall(stringResource(R.string.meal_time_tip))
        Spacer(modifier = Modifier.height(24.dp))
        CommonButton(
            state.selectedTime.value.format(DateTimeFormatter.ofPattern("HH:mm")),
            image = R.drawable.time
        ) {
            onEvent(EditSelectionEvent.ChooseTime(context))
        }
        Spacer(modifier = Modifier.height(48.dp))

        DoneButton(text = stringResource(R.string.apply)) { onEvent(EditSelectionEvent.Done) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditSelectionContent() {
    WeekOnAPlateTheme {
        EditSelectionView(
            EditSelectionUIState(
                title = R.string.specify_selection,
                placeholder = R.string.enter_text_note
            )
        ) {}
    }
}