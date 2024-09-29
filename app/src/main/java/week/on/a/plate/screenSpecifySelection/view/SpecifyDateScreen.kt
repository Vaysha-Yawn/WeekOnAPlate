package week.on.a.plate.screenSpecifySelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screenSpecifySelection.state.SpecifySelectionUIState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.ButtonsCounter
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall


@Composable
fun SpecifyDateScreen(
    state: SpecifySelectionUIState,
    onEvent: (SpecifySelectionEvent) -> Unit,
    onEventMain: (MainEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            CloseButton { onEvent(SpecifySelectionEvent.Back) }
            TextTitleItalic(
                text = stringResource(R.string.specify_selection),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(text = stringResource(R.string.title_in_generally), modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckButton(checked = state.checkWeek) {
                if (state.checkWeek.value) {
                    state.checkWeek.value = false
                } else {
                    state.checkWeek.value = true
                    state.checkDayCategory.value = null
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextBody(text = CategoriesSelection.ForWeek.fullName, modifier = Modifier.clickable {
                if (state.checkWeek.value) {
                    state.checkWeek.value = false
                } else {
                    state.checkWeek.value = true
                    state.checkDayCategory.value = null
                }
            })
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(text = stringResource(R.string.title_or_detailed), modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
        ) {
            CommonButton(
                state.date.value?.dateToString()
                    ?: stringResource(R.string.select_day),
                image = R.drawable.calendar_no_dark
            ) {
                onEvent(SpecifySelectionEvent.ChooseDate)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextBody(text = stringResource(R.string.title_eating))
            Spacer(modifier = Modifier.height(24.dp))
            listOf(
                CategoriesSelection.NonPosed,
                CategoriesSelection.Breakfast,
                CategoriesSelection.Lunch,
                CategoriesSelection.Dinner
            ).forEach { it ->
                Row() {
                    val check = remember {
                        mutableStateOf(it == state.checkDayCategory.value)
                    }
                    LaunchedEffect(key1 = state.checkDayCategory.value) {
                        check.value = it == state.checkDayCategory.value
                    }
                    CheckButton(checked = check) {
                        if (state.checkWeek.value) {
                            state.checkWeek.value = false
                        }
                        if (check.value) {
                            state.checkDayCategory.value = null
                        } else {
                            state.checkDayCategory.value = it
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    TextBody(text = it.fullName, modifier = Modifier.clickable {
                        if (state.checkWeek.value) {
                            state.checkWeek.value = false
                        }
                        if (check.value) {
                            state.checkDayCategory.value = null
                        } else {
                            state.checkDayCategory.value = it
                        }
                    })
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(text = "Количество порций", modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonsCounterSmall(value = state.portionsCount,
                minus = { if (state.portionsCount.intValue>0){
                    state.portionsCount.intValue = state.portionsCount.intValue.minus(1)}},
                plus = {
                    state.portionsCount.intValue = state.portionsCount.intValue.plus(1)
                })
        }
        Spacer(modifier = Modifier.weight(1f))
        val messageError = stringResource(id = R.string.message_non_validate_place_position_in_menu)
        DoneButton(stringResource(id = R.string.apply)) {
            if (state.date.value != null && (state.checkWeek.value || state.checkDayCategory.value != null)) {
                onEvent(SpecifySelectionEvent.Done)
            } else {
                onEventMain(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {
        SpecifyDateScreen(SpecifySelectionUIState(), {}) {}
    }
}