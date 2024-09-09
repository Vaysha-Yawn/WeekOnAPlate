package week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen

import androidx.compose.foundation.background
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import week.on.a.plate.R
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.ui.theme.ColorButtonGreen
import week.on.a.plate.ui.theme.ColorTextBlack
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipe() {
    val state = rememberDatePickerState()
    val showState = remember { mutableStateOf(false) }
    val checkWeek = remember {
        mutableStateOf<Boolean>(false)
    }
    val checkDayCategory = remember {
        mutableStateOf<CategoriesSelection?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        CloseButton {

        }
        TextDisplayItalic(text = "Добавить в меню", textAlign = TextAlign.End)
        Spacer(modifier = Modifier.height(24.dp))



        TextTitleItalic(text = "В общем", modifier = Modifier.padding(start = 24.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            CheckButton(checked = checkWeek) {
                if (checkWeek.value){
                    checkWeek.value = false
                }else{
                checkWeek.value = true
                checkDayCategory.value = null
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            TextBody(text = CategoriesSelection.ForWeek.fullName)
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextTitleItalic(text = "Или подробнее", modifier = Modifier.padding(start = 24.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            CommonButton(
                state.selectedDateMillis?.dateToString()?:"Выберите день",
                R.drawable.calendar
            ) {
                showState.value = true
            }

            Spacer(modifier = Modifier.height(24.dp))
            TextBody(text = "Прием пищи:")
            Spacer(modifier = Modifier.height(24.dp))
            listOf(
                CategoriesSelection.NonPosed,
                CategoriesSelection.Breakfast,
                CategoriesSelection.Lunch,
                CategoriesSelection.Dinner
            ).forEach { it ->
                Row() {
                    val check = remember {
                        mutableStateOf(it == checkDayCategory.value)
                    }
                    LaunchedEffect(key1 = checkDayCategory.value) {
                        check.value = it==checkDayCategory.value
                    }
                    CheckButton(checked = check) {
                        if (checkWeek.value){
                            checkWeek.value = false
                        }
                        if (check.value){
                            checkDayCategory.value = null
                        }else{
                            checkDayCategory.value = it
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    TextBody(text = it.fullName)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DoneButton("Готово") {

        }
    }
    DatePickerMy(state, showState) {
        showState.value = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMy(state: DatePickerState, showState: MutableState<Boolean>, done: () -> Unit) {
    if (showState.value) {
        Dialog(onDismissRequest = { showState.value = false }) {
            Column(
                Modifier
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
            ) {
                DatePicker(
                    state = state, colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        headlineContentColor = MaterialTheme.colorScheme.onBackground,
                        weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                        subheadContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationContentColor = MaterialTheme.colorScheme.onBackground,
                        yearContentColor = MaterialTheme.colorScheme.onBackground,
                        disabledYearContentColor = MaterialTheme.colorScheme.onBackground,
                        currentYearContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedYearContentColor = ColorTextBlack,
                        disabledSelectedYearContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedYearContainerColor = ColorButtonGreen,
                        disabledSelectedYearContainerColor = MaterialTheme.colorScheme.onBackground,
                        dayContentColor = MaterialTheme.colorScheme.onBackground,
                        disabledDayContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedDayContentColor = ColorTextBlack,
                        disabledSelectedDayContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedDayContainerColor = ColorButtonGreen,
                        disabledSelectedDayContainerColor = MaterialTheme.colorScheme.onBackground,
                        todayContentColor = MaterialTheme.colorScheme.onBackground,
                        todayDateBorderColor = ColorButtonGreen,
                        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onBackground,
                        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onBackground,
                        dividerColor = MaterialTheme.colorScheme.outline,
                    )
                )
                DoneButton(text = "Подтвердить", Modifier.padding(12.dp)) {
                    done()
                }
            }
        }
    }
}


fun Long.dateToString(): String {
    val dates = Date(this)
    val formattedDate = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(dates)
    return formattedDate
}

fun Long.dateToLocalDate(): LocalDate {
    val dates = Date(this)
    val localDate = dates.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate
}

@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {
        AddRecipe()
    }
}