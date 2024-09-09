package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.data.example.ingredientTomato
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.uitools.EditNumberLine
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.core.uitools.ingredientCard.CardIngredient
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


//todo хранить состояния в stateD, существлять действия с MenuEvent,
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditIngredientBottomDialog(
    state: MenuIUState,
    onEvent: (MenuEvent) -> Unit
) {
    val stateD = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    val text = remember { mutableStateOf("") }
    val number = remember { mutableDoubleStateOf(0.0) }
    val ingredient = remember { mutableStateOf(ingredientTomato) }
    BottomDialogContainer(stateD){
        EditIngredientBottomDialogContent(text, number, ingredient, state, onEvent) {
            //todo done
            scope.launch { stateD.hide() }
        }
    }
}

@Composable
fun EditIngredientBottomDialogContent(
    text: MutableState<String>,
    number: MutableState<Double>,
    ingredient: MutableState<IngredientView>,
    state: MenuIUState,
    onEvent: (MenuEvent) -> Unit,
    clickToDone: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {

        TextBody(text = "Ингредиент", modifier = Modifier.padding(horizontal = 48.dp))
        Spacer(modifier = Modifier.height(12.dp))
        CardIngredient(ingredient = ingredient.value) {
            Image(
                painter = painterResource(id = R.drawable.find_replace),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        //onEvent(MenuEvent.FindIngredient())
                    }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            text,
            "Пояснение",
            "Кусочками...", modifier = Modifier.padding(horizontal = 24.dp)
        ) { value ->
            text.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            EditNumberLine(
                number,
                "Количество, ${ingredient.value.measure}",
                "0.0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) { newnum ->
                number.value = newnum
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = "Добавить", modifier = Modifier.padding(horizontal = 24.dp)) {
            //onEvent(MenuEvent.AddNote(text.value))
            //onEvent(MenuEvent.EditNote(text.value))
            //onEvent(MenuEvent.CloseAddNoteDialog)
            clickToDone()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditIngredientBottomDialog() {
    WeekOnAPlateTheme {
        val uiState = MenuIUState.MenuIUStateExample
        val text = remember { mutableStateOf("") }
        val number = remember { mutableStateOf(0.0) }
        val ingredient = remember { mutableStateOf(ingredientTomato) }
        EditIngredientBottomDialogContent(text, number, ingredient, uiState, {}) {}
    }
}