package week.on.a.plate.fullScreenDialogs.data

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.R
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.tools.dateToLocalDate
import week.on.a.plate.menuScreen.data.eventData.ActionMenuDBData
import java.time.LocalDate

sealed class FullScreenDialogData(val onEvent: (FullScreenDialogsEvent) -> Unit) {

    val close = {
        onEvent(FullScreenDialogsEvent.NavigateBack)
    }

    class AddPositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, val recipe: Position.PositionRecipeView,
        val date: LocalDate, val category: String, onEvent: (FullScreenDialogsEvent) -> Unit
    ) : FullScreenDialogData(onEvent) {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
        val titleRes = R.string.add_to_menu

        @OptIn(ExperimentalMaterial3Api::class)
        val setUpStartSettings = {
            date.toEpochDay()
            when (category) {
                CategoriesSelection.ForWeek.fullName -> {
                    checkWeek.value = true
                    checkDayCategory.value = null
                }

                CategoriesSelection.NonPosed.fullName -> {
                    checkWeek.value = false
                    checkDayCategory.value = CategoriesSelection.NonPosed
                }

                CategoriesSelection.Breakfast.fullName -> {
                    checkWeek.value = false
                    checkDayCategory.value = CategoriesSelection.Breakfast
                }

                CategoriesSelection.Lunch.fullName -> {
                    checkWeek.value = false
                    checkDayCategory.value = CategoriesSelection.Lunch
                }

                CategoriesSelection.Dinner.fullName -> {
                    checkWeek.value = false
                    checkDayCategory.value = CategoriesSelection.Dinner
                }
            }
            state.selectedDateMillis = date.toEpochDay()
        }

        @OptIn(ExperimentalMaterial3Api::class)
        val done = {
            val eventAfter: (Long) -> Unit = { id ->
                onEvent(
                    FullScreenDialogsEvent.ActionMenuBD(
                        ActionMenuDBData.AddRecipePositionInMenuDB(
                            id,
                            this.recipe
                        )
                    )
                )
            }
            onEvent(
                FullScreenDialogsEvent.GetSelIdAndCreate(
                    eventAfter, this.state.selectedDateMillis!!.dateToLocalDate(),
                    if (this.checkWeek.value) {
                        CategoriesSelection.ForWeek
                    } else {
                        this.checkDayCategory.value!!
                    },
                )
            )
            onEvent(FullScreenDialogsEvent.NavigateBack)
        }
    }

    class MovePositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState,
        val position: Position,
        onEvent: (FullScreenDialogsEvent) -> Unit,
    ) : FullScreenDialogData(onEvent) {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
        val titleRes = R.string.move_position

        @OptIn(ExperimentalMaterial3Api::class)
        val done = {
            onEvent(
                FullScreenDialogsEvent.ActionMenuBD(
                    ActionMenuDBData.MovePositionInMenuDB(
                        state.selectedDateMillis!!.dateToLocalDate(),
                        if (checkWeek.value) {
                            CategoriesSelection.ForWeek
                        } else {
                            checkDayCategory.value!!
                        },
                        position
                    )
                )
            )
            onEvent(FullScreenDialogsEvent.NavigateBack)
        }
    }

    class DoublePositionToMenu @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState,
        val position: Position,
        onEvent: (FullScreenDialogsEvent) -> Unit,
    ) : FullScreenDialogData(onEvent) {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)
        val titleRes = R.string.double_position

        @OptIn(ExperimentalMaterial3Api::class)
        val done = {
            onEvent(
                FullScreenDialogsEvent.ActionMenuBD(
                    ActionMenuDBData.DoublePositionInMenuDB(
                        state.selectedDateMillis!!.dateToLocalDate(),
                        if (checkWeek.value) {
                            CategoriesSelection.ForWeek
                        } else {
                            checkDayCategory.value!!
                        },
                        position
                    )
                )
            )
            onEvent(FullScreenDialogsEvent.NavigateBack)
        }
    }

    class SpecifyDate @OptIn(ExperimentalMaterial3Api::class) constructor(
        val state: DatePickerState, onEventVM: (FullScreenDialogsEvent) -> Unit
    ) : FullScreenDialogData(onEventVM) {
        val checkWeek = mutableStateOf<Boolean>(false)
        val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
        val showDatePicker: MutableState<Boolean> = mutableStateOf(false)

        @OptIn(ExperimentalMaterial3Api::class)
        val done = {
            onEvent(
                FullScreenDialogsEvent.NavigateToMenuForCreate(
                    state.selectedDateMillis!!.dateToLocalDate(),
                    if (checkWeek.value) {
                        CategoriesSelection.ForWeek
                    } else {
                        checkDayCategory.value!!
                    },
                )
            )
        }
    }
}