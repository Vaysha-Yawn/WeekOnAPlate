package week.on.a.plate.dialogAddCategory.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.dialogAddCategory.state.AddCategoryUIState
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun AddCategory(
    snackBarState: SnackbarHostState,
    state: AddCategoryUIState,
    done: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = stringResource(R.string.add_category),
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            state.text,
            stringResource(R.string.enter_category_name),
            modifier = Modifier
        ) { value ->
            state.text.value = value
        }
        val messageError = stringResource(R.string.error_enter_title)
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (state.text.value != "") {
                done()
            } else {
                coroutineScope.launch {
                    snackBarState.showSnackbar(messageError)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddCategory() {
    WeekOnAPlateTheme {
        val state = AddCategoryUIState()
        val snackBar = SnackbarHostState()
        AddCategory(snackBar, state) {}

    }
}