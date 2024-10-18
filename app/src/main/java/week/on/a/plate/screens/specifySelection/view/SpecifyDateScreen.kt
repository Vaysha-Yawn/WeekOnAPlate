package week.on.a.plate.screens.specifySelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screens.menu.view.topBar.TitleMenuSmall
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.state.SpecifySelectionUIState


@Composable
fun SpecifyDateScreen(
    state: SpecifySelectionUIState,
    onEvent: (SpecifySelectionEvent) -> Unit,
    onEventMain: (MainEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CloseButton { onEvent(SpecifySelectionEvent.Back) }
            TextTitleItalic(
                text = stringResource(R.string.specify_selection),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(36.dp))
        ChangePotionsCountSpecifySel(state.portionsCount)

        Spacer(modifier = Modifier.height(36.dp))
        CommonButton(
            state.date.value?.dateToString()
                ?: stringResource(R.string.select_day),
            image = R.drawable.calendar_no_dark
        ) {
            onEvent(SpecifySelectionEvent.ChooseDate)
        }

        Spacer(modifier = Modifier.height(24.dp))

        ChooseSelectionSpecifySelection(state, onEvent, this)

        Spacer(modifier = Modifier.height(24.dp))

        val messageError =
            stringResource(id = R.string.message_non_validate_place_position_in_menu)
        DoneButton(
            stringResource(id = R.string.apply),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            if (state.date.value != null && (state.checkWeek.value || state.checkDayCategory.value != null)) {
                onEvent(SpecifySelectionEvent.Done)
            } else {
                onEventMain(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}

@Composable
fun ChooseSelectionSpecifySelection(
    state: SpecifySelectionUIState,
    onEvent: (SpecifySelectionEvent) -> Unit,
    columnScope: ColumnScope
) {
    with(columnScope){
        /*TextTitleItalic(
            text = stringResource(R.string.title_in_generally),
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))*/
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckBoxAndText(CategoriesSelection.ForWeek.fullName, state.checkWeek) {
                if (state.checkWeek.value) {
                    state.checkWeek.value = false
                } else {
                    state.checkWeek.value = true
                    state.checkDayCategory.value = null
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
       /* TextTitleItalic(
            text = stringResource(R.string.title_or_detailed),
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))*/
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .weight(1f),
        ) {
            if (state.allSelectionsIdDay.value.isEmpty()) {
                TextBody(text = "- Выберите сначала день")
            } else {
                TitleMenuSmall(name = "Указать другой приём пищи") {
                    onEvent(SpecifySelectionEvent.AddCustomSelection)
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
fun ChangePotionsCountSpecifySel(portionsCount: MutableIntState) {
    TextTitleItalic(text = "Количество порций", modifier = Modifier.padding(start = 12.dp))
    Spacer(modifier = Modifier.height(12.dp))
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonsCounterSmall(value = portionsCount,
            minus = {
                if (portionsCount.intValue > 0) {
                    portionsCount.intValue = portionsCount.intValue.minus(1)
                }
            },
            plus = {
                portionsCount.intValue = portionsCount.intValue.plus(1)
            })
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
        SpecifyDateScreen(SpecifySelectionUIState().apply {
            allSelectionsIdDay.value = listOf("Завтрак")
        }, {}) {}
    }
}