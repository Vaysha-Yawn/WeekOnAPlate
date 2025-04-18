package week.on.a.plate.dialogs.editOneString.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState

@Composable
fun EditOneStringContent(
    state: EditOneStringUIState,
    done: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val isError: MutableState<Boolean> = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
            .imePadding()) {
        TextTitle(text = stringResource(state.title))
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            state.text,
            stringResource(state.placeholder), modifier = Modifier.focusRequester(focusRequester), isRequired = true, isError = isError
        )
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = stringResource(R.string.apply)) {
            if (state.text.value ==""){
                isError.value = true
            }else {
                done()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditNoteBottomDialog() {
    WeekOnAPlateTheme {
        EditOneStringContent(EditOneStringUIState()){}
    }
}