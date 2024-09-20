package week.on.a.plate.core.dialogs.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddCategory(
    name: MutableState<String>,
    onEvent: (MainEvent) -> Unit,
    done: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = "Добавить категорию",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            name,
            "Введите название категории здесь",
            "Введите название категории здесь", modifier = Modifier
        ) { value ->
            name.value = value
        }
        val messageError = "Пожалуйста введите название"
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (name.value != "") {
                done()
            } else {
                onEvent(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddCategory() {
    WeekOnAPlateTheme {
        val name = remember { mutableStateOf("") }
        AddCategory(name, {}) {}

    }
}