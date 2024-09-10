package week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoublePositionDialogContent(
    data: DialogMenuData.DoublePositionToMenu,
    onEvent: (MenuEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent("Дублировать позицию",
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,
        {
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionDBData.DoublePositionInMenuDB(
                        data.state.selectedDateMillis!!.dateToLocalDate(),
                        data.checkDayCategory.value,
                        data.checkWeek.value,
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
    AddMoveDoubleRecipeDialogContent("Переместить позицию",
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,
        {
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionDBData.MovePositionInMenuDB(
                        data.state.selectedDateMillis!!.dateToLocalDate(),
                        data.checkDayCategory.value,
                        data.checkWeek.value,
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

    AddMoveDoubleRecipeDialogContent("Добавить в меню",
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker,
        {
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionDBData.AddPositionInMenuDB(
                        data.state.selectedDateMillis!!.dateToLocalDate(),
                        data.checkDayCategory.value,
                        data.checkWeek.value,
                        data.recipe
                    )
                )
            )
            onEvent(MenuEvent.CloseDialog)
        }) {
        onEvent(MenuEvent.CloseDialog)
    }
}