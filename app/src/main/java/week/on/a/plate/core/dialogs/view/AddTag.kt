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
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.TagCategoryView
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddTag(
    name: MutableState<String>,
    category: MutableState<TagCategoryView?>,
    onEvent: (MainEvent) -> Unit,
    done: () -> Unit,
    chooseCategory: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = "Добавить тэг",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(
            text = "Название тэга",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        EditTextLine(
            name,
            "Введите название тэга здесь",
            "Введите название тэга здесь", modifier = Modifier
        ) { value ->
            name.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(
            text = "Категория",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        CommonButton(
            if (category.value==null) "Выбрать категорию" else category.value!!.name,
            R.drawable.search,
        ) {
            chooseCategory()
        }
        val messageError = "Пожалуйста введите название и выберите категорию"
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (name.value != "" && category.value != null) {
                done()
            } else {
                onEvent(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddTag() {
    WeekOnAPlateTheme {
        val name = remember { mutableStateOf("") }
        val category = remember { mutableStateOf<TagCategoryView?>(tags[0]) }
        AddTag(name, category, {}, {}) {}

    }
}