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
import week.on.a.plate.core.data.example.ingredients
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddIngredient(
    name: MutableState<String>,
    measure: MutableState<String>,
    category: MutableState<IngredientCategoryView?>,
    photoUri: MutableState<String>,
    photoFromGalleryUri: MutableState<String>,
    onEvent: (MainEvent) -> Unit,
    done: () -> Unit,
    chooseCategory: () -> Unit,
    setPhotoUrlFromGallery: () -> Unit,
) {
    Column(modifier = Modifier.padding(24.dp)) {
        TextTitleItalic(
            text = "Добавить ингредиент",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(
            text = "Название ингредиента",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        EditTextLine(
            name,
            "Введите название ингредиента",
            "Введите название ингредиента", modifier = Modifier
        ) { value ->
            name.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextTitleItalic(
            text = "Мера измерения",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        EditTextLine(
            measure,
            "Введите меру измерения",
            "Введите меру измерения", modifier = Modifier
        ) { value ->
            measure.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        TextTitleItalic(
            text = "Категория",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        CommonButton(
            if (category.value == null) "Выбрать категорию" else category.value!!.name,
            R.drawable.search,
        ) {
            chooseCategory()
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(
            text = "Фото",
            modifier = Modifier.padding(start = 24.dp).padding(bottom = 12.dp), textAlign = TextAlign.Start
        )
        CommonButton(
            if (photoFromGalleryUri.value == "") "Выбрать фото из галлереи" else photoFromGalleryUri.value,
            R.drawable.search,
        ) {
            setPhotoUrlFromGallery()
        }
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            photoUri,
            "Или введите ссылку на изображение",
            "Или введите ссылку на изображение", modifier = Modifier
        ) { value ->
            photoUri.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))

        val messageError = "Пожалуйста введите название, меру и выберите категорию"
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (name.value != "" && category.value != null && measure.value != "") {
                done()
            } else {
                onEvent(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddIngredient() {
    WeekOnAPlateTheme {
        val name = remember { mutableStateOf("") }
        val category = remember { mutableStateOf<IngredientCategoryView?>(ingredients[0]) }
        val photoUri = remember { mutableStateOf("") }
        val photoGalleryUri = remember { mutableStateOf("") }
        val measure = remember { mutableStateOf("") }
        AddIngredient(name, measure, category,photoUri,photoGalleryUri,{},{}, {}, {})
    }
}