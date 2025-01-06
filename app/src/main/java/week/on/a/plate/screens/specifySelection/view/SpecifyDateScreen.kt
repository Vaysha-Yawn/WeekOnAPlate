package week.on.a.plate.screens.specifySelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TitleMenuSmall
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.state.SpecifySelectionUIState

@Composable
fun ChooseSelectionSpecifySelection(
    state: SpecifySelectionUIState,
    onEvent: (SpecifySelectionEvent) -> Unit,
    columnScope: ColumnScope
) {
    with(columnScope) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckBoxAndText(stringResource(ForWeek.fullName) , state.checkWeek) {
                if (state.checkWeek.value) {
                    state.checkWeek.value = false
                } else {
                    state.checkWeek.value = true
                    state.checkDayCategory.value = null
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .weight(1f),
        ) {
            if (state.allSelectionsIdDay.value.isEmpty()) {
                TextBody(text = stringResource(R.string.mess_select_day_first))
            } else {
                TitleMenuSmall(name = stringResource(R.string.mess_specify_another_meal)) {
                    onEvent(SpecifySelectionEvent.AddCustomSelection )
                }
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(state.allSelectionsIdDay.value.size) {
                        val item = state.allSelectionsIdDay.value[it]
                        val check = remember {
                            mutableStateOf(item == state.checkDayCategory.value)
                        }
                        LaunchedEffect(key1 = state.checkDayCategory.value) {
                            check.value = item == state.checkDayCategory.value
                        }
                        CheckBoxAndText(item, check) {
                            if (state.checkWeek.value) {
                                state.checkWeek.value = false
                            }
                            if (check.value) {
                                state.checkDayCategory.value = null
                            } else {
                                state.checkDayCategory.value = item
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CheckBoxAndText(text: String, stateCheck: MutableState<Boolean>, onCheck: () -> Unit) {
    Row() {
        CheckButton(checked = stateCheck) {
            onCheck()
        }
        Spacer(modifier = Modifier.width(12.dp))
        TextBody(text = text, modifier = Modifier.clickable {
            onCheck()
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {

    }
}