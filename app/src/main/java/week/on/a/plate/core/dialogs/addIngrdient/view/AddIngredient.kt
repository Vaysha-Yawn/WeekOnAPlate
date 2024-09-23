package week.on.a.plate.core.dialogs.addIngrdient.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.dialogs.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.core.dialogs.addIngrdient.state.AddIngredientUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddIngredient(
    state:AddIngredientUIState,
    onEventMain: (MainEvent) -> Unit,
    onEvent: (AddIngredientEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = "Добавить ингредиент",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(36.dp))
        TextTitleItalic(
            text = "Название ингредиента",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        EditTextLine(
            state.name,
            "Введите название ингредиента",
            "Введите название ингредиента", modifier = Modifier
        ) { value ->
            state.name.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextTitleItalic(
            text = "Мера измерения",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        EditTextLine(
            state.measure,
            "Введите меру измерения",
            "Введите меру измерения", modifier = Modifier
        ) { value ->
            state.measure.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextTitleItalic(
            text = "Категория",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        CommonButton(
            if (state.category.value == null) "Выбрать категорию" else state.category.value!!.name,
            R.drawable.search,
        ) {
           onEvent(AddIngredientEvent.ChooseCategory)
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(
            text = "Фото",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
       /* CommonButton(
            if (photoFromGalleryUri.value == "") "Выбрать фото из галлереи" else photoFromGalleryUri.value,
            R.drawable.search,
        ) {
            setPhotoUrlFromGallery()
        }
        Spacer(modifier = Modifier.height(24.dp))*/
        EditTextLine(
            state.photoUri,
            "Введите ссылку на изображение",
            "Введите ссылку на изображение", modifier = Modifier
        ) { value ->
            state.photoUri.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        val messageError = "Пожалуйста введите название, меру и выберите категорию"
        Spacer(modifier = Modifier.height(24.dp))
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
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddIngredient() {
    WeekOnAPlateTheme {
        val state = AddIngredientUIState("", "", null, "")
        AddIngredient(state, {},{},)
    }
}