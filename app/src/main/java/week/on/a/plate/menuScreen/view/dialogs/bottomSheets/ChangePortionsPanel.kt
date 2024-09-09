package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.ingredientTomato
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.ButtonsCounter
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

//todo хранить состояния в stateD, существлять действия с MenuEvent,
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePortionsBottomDialog(
    state: MenuIUState,
    onEvent: (MenuEvent) -> Unit
) {
    val stateD = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val number = remember { mutableStateOf(0) }
    BottomDialogContainer(stateD){
        ChangePortionsPanel(number) {
            //todo done
            scope.launch { stateD.hide() }
        }
    }
}

@Composable
fun ChangePortionsPanel(count: MutableState<Int>, done: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(text = "Изменить количество порций")
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonsCounter(count, {
                if (count.value > 0) {
                    count.value -= 1
                }
            }, {
                if (count.value < 20) {
                    count.value += 1
                }
            })
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton("Готово") { done() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePortionsPanel() {
    WeekOnAPlateTheme {
        val state = remember {
            mutableStateOf(0)
        }
        ChangePortionsPanel(state){}
    }
}