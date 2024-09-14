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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionRecipeExample
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMoveDoubleRecipeDialogContent(
    title: String,
    state: DatePickerState,
    checkWeek: MutableState<Boolean>,
    checkDayCategory: MutableState<CategoriesSelection?>,
    showDatePicker: MutableState<Boolean>,
    event:(MenuEvent)->Unit,
    done: () -> Unit,
    close: () -> Unit,
) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            CloseButton { close() }
            Spacer(modifier = Modifier.height(12.dp))
            TextTitleItalic(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextTitleItalic(text = stringResource(R.string.title_in_generally), modifier = Modifier)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                CheckButton(checked = checkWeek) {
                    if (checkWeek.value) {
                        checkWeek.value = false
                    } else {
                        checkWeek.value = true
                        checkDayCategory.value = null
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                TextBody(text = CategoriesSelection.ForWeek.fullName)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextTitleItalic(text = stringResource(R.string.title_or_detailed), modifier = Modifier)
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 12.dp),
            ) {
                CommonButton(
                    state.selectedDateMillis?.dateToString() ?: stringResource(R.string.select_day),
                    R.drawable.calendar_no_dark
                ) {
                    showDatePicker.value = true
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
                            mutableStateOf(it == checkDayCategory.value)
                        }
                        LaunchedEffect(key1 = checkDayCategory.value) {
                            check.value = it == checkDayCategory.value
                        }
                        CheckButton(checked = check) {
                            if (checkWeek.value) {
                                checkWeek.value = false
                            }
                            if (check.value) {
                                checkDayCategory.value = null
                            } else {
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
            val messageError = stringResource(id = R.string.message_non_validate_place_position_in_menu)
            DoneButton("Готово") {
                if (checkWeek.value || (state.selectedDateMillis?.dateToLocalDate() != null && checkDayCategory.value != null)) {
                    done()
                } else {
                    event(MenuEvent.ShowSnackBar(messageError))
                }
            }
        }
        DatePickerMy(
            state,
            showDatePicker,
            { showDatePicker.value = false }) { showDatePicker.value = false }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {
        AddRecipeDialogContent(
            DialogMenuData.AddPositionToMenu(
                rememberDatePickerState(),
                positionRecipeExample, LocalDate.now(), ""
            )
        ) {}
    }
}