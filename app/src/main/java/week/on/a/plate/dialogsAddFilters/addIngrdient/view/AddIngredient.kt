package week.on.a.plate.dialogsAddFilters.addIngrdient.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.dialogsAddFilters.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.dialogsAddFilters.addIngrdient.state.AddIngredientUIState
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody

@Composable
fun AddIngredient(
    state: AddIngredientUIState,
    onEventMain: (MainEvent) -> Unit,
    onEvent: (AddIngredientEvent) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(24.dp)) {
        item {
            TextTitleItalic(
                text = "Добавить ингредиент",
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            TextBody(
                text = "Название ингредиента",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            EditTextLine(
                state.name,
                "Введите название ингредиента",
                modifier = Modifier
            ) { value ->
                state.name.value = value
            }
            Spacer(modifier = Modifier.height(24.dp))

            TextBody(
                text = "Мера измерения",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            EditTextLine(
                state.measure,
                "Введите меру измерения",
                modifier = Modifier
            ) { value ->
                state.measure.value = value
            }
            Spacer(modifier = Modifier.height(24.dp))

            TextBody(
                text = "Категория",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            CommonButton(
                if (state.category.value == null) "Выбрать категорию" else state.category.value!!.name,
                image = R.drawable.search,
            ) {
                onEvent(AddIngredientEvent.ChooseCategory)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextBody(
                text = "Фото",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            EditTextLine(
                state.photoUri,
                "Введите ссылку на изображение",
                modifier = Modifier
            ) { value ->
                state.photoUri.value = value
            }
            val messageError = "Пожалуйста введите название, меру и выберите категорию"
            Spacer(modifier = Modifier.height(36.dp))
            DoneButton(
                text = stringResource(id = R.string.add),
                modifier = Modifier
            ) {
                if (state.name.value != "" && state.category.value != null && state.measure.value != "") {
                    onEvent(AddIngredientEvent.Done)
                } else {
                    onEventMain(MainEvent.ShowSnackBar(messageError))
                }
            }
            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddIngredient() {
    WeekOnAPlateTheme {
        val state = AddIngredientUIState("", "", null, "")
        AddIngredient(state, {}, {})
    }
}