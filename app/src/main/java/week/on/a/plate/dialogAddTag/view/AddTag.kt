package week.on.a.plate.dialogAddTag.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import week.on.a.plate.dialogAddTag.event.AddTagEvent
import week.on.a.plate.dialogAddTag.state.AddTagUIState
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody

@Composable
fun AddTag(
    snackBarState: SnackbarHostState,
    state: AddTagUIState,
    onEvent: (AddTagEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = "Добавить тэг",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(
            text = "Название тэга",
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Start
        )
        EditTextLine(
            state.text,
            "Введите название тэга здесь",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(
            text = "Категория",
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Start
        )
        CommonButton(
            if (state.category.value?.name == "") "Выбрать категорию" else state.category.value?.name?:"",
            image = R.drawable.search,
        ) {
            onEvent(AddTagEvent.ChooseCategory)
        }
        val messageError = "Пожалуйста введите название и выберите категорию"
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(36.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (state.text.value != "" && state.category.value != null) {
                onEvent(AddTagEvent.Done)
            } else {
                coroutineScope.launch {
                    snackBarState.showSnackbar(messageError)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewAddTag() {
    WeekOnAPlateTheme {
        val state = AddTagUIState()
        val snackBar = SnackbarHostState()
        AddTag(snackBar, state) {}

    }
}