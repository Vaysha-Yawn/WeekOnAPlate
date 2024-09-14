package week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import week.on.a.plate.R
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecifyDatePositionDialogContent(
    data: DialogMenuData.SpecifyDate,
    onEvent: (MenuEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent("Добавить позицию",
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker, onEvent,
        {
            onEvent(MenuEvent.GetSelIdAndCreate(data.eventAfter, data.state.selectedDateMillis!!.dateToLocalDate(),
                if (data.checkWeek.value) {
                    CategoriesSelection.ForWeek
                } else {
                    data.checkDayCategory.value!!
                },))
            onEvent(MenuEvent.CloseDialog)
        }) {
        onEvent(MenuEvent.CloseDialog)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoublePositionDialogContent(
    data: DialogMenuData.DoublePositionToMenu,
    onEvent: (MenuEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent("Дублировать позицию",
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,onEvent,
        {
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionDBData.DoublePositionInMenuDB(
                        data.state.selectedDateMillis!!.dateToLocalDate(),
                        if (data.checkWeek.value) {
                            CategoriesSelection.ForWeek
                        } else {
                            data.checkDayCategory.value!!
                        },
                        data.position
                    )
                )
            )
            onEvent(MenuEvent.CloseDialog)
        }) {
        onEvent(MenuEvent.CloseDialog)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovePositionDialogContent(
    data: DialogMenuData.MovePositionToMenu,
    onEvent: (MenuEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent(
        stringResource(R.string.move_position),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,onEvent,
        {
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionDBData.MovePositionInMenuDB(
                        data.state.selectedDateMillis!!.dateToLocalDate(),
                        if (data.checkWeek.value) {
                            CategoriesSelection.ForWeek
                        } else {
                            data.checkDayCategory.value!!
                        },
                        data.position
                    )
                )
            )
            onEvent(MenuEvent.CloseDialog)
        }) {
        onEvent(MenuEvent.CloseDialog)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeDialogContent(data: DialogMenuData.AddPositionToMenu, onEvent: (MenuEvent) -> Unit) {

    when (data.category) {
        CategoriesSelection.ForWeek.fullName -> {
            data.checkWeek.value = true
            data.checkDayCategory.value = null
        }

        CategoriesSelection.NonPosed.fullName -> {
            data.checkWeek.value = false
            data.checkDayCategory.value = CategoriesSelection.NonPosed
        }

        CategoriesSelection.Breakfast.fullName -> {
            data.checkWeek.value = false
            data.checkDayCategory.value = CategoriesSelection.Breakfast
        }

        CategoriesSelection.Lunch.fullName -> {
            data.checkWeek.value = false
            data.checkDayCategory.value = CategoriesSelection.Lunch
        }

        CategoriesSelection.Dinner.fullName -> {
            data.checkWeek.value = false
            data.checkDayCategory.value = CategoriesSelection.Dinner
        }
    }

    data.state.selectedDateMillis = data.date.toEpochDay()

    AddMoveDoubleRecipeDialogContent(
        stringResource(R.string.add_to_menu),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,onEvent,
        {
            val eventAfter:(Long)->Unit = {id->
                onEvent(
                    MenuEvent.ActionDBMenu(
                        ActionDBData.AddRecipePositionInMenuDB(
                            id,
                            data.recipe
                        )
                    )
                )
            }
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.GetSelIdAndCreate(eventAfter, data.state.selectedDateMillis!!.dateToLocalDate(),
                if (data.checkWeek.value) {
                    CategoriesSelection.ForWeek
                } else {
                    data.checkDayCategory.value!!
                },))
        }) {
        onEvent(MenuEvent.CloseDialog)
    }
}