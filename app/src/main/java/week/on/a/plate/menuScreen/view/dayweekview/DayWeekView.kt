package week.on.a.plate.menuScreen.view.dayweekview

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.view.recipeBlock.BlockSelection
import week.on.a.plate.menuScreen.view.recipeBlock.BlockSelectionSmall
import week.on.a.plate.ui.theme.Typography
import java.time.LocalDate

@Composable
fun WeekView(
    week: WeekView,
    editing: MutableState<Boolean>,
    vm: MenuViewModel
) {
    LazyColumn {
        item {
            BlockSelection(
                selection = week.selection,
                editing = editing,
                actionAdd = { vm.actionAddRecipeToCategory(LocalDate.parse("0"), week.selection.category) },
                actionNavToFullRecipe = { rec -> vm.actionNavToFullRecipe(rec) },
                checkAction = {id -> vm.actionCheckRecipe(id) },
                switchEditMode = { vm.actionSwitchEditMode() },
                actionEdit = { id -> vm.actionEdit(id) },
                actionRecipeToNextStep = { id -> vm.actionRecipeToNextStep(id) },
                getCheckState = { id -> vm.getCheckState(id)}
            )
        }
        for (day in week.days) {
            item {
                TextInApp(
                    text = day.dayInWeek.fullName,
                    textStyle = Typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(Modifier.height(10.dp))
            }
            items(day.selections.size) { index ->
                BlockSelectionSmall(
                    selection = day.selections[index],
                    editing = editing,
                    actionAdd = { vm.actionAddRecipeToCategory(day.date, day.selections[index].category) },
                    actionNavToFullRecipe = { rec -> vm.actionNavToFullRecipe(rec) },
                    checkAction = {id -> vm.actionCheckRecipe(id) },
                    switchEditMode = { vm.actionSwitchEditMode() },
                    actionEdit = { id -> vm.actionEdit(id) },
                    actionRecipeToNextStep = { id -> vm.actionRecipeToNextStep(id) },
                    getCheckState = { id -> vm.getCheckState(id)}
                )
            }
            item {
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun DayView(day: DayView, editing: MutableState<Boolean>, vm: MenuViewModel) {
    LazyColumn {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index],
                editing = editing,
                actionAdd = { vm.actionAddRecipeToCategory(day.date, day.selections[index].category) },
                actionNavToFullRecipe = { rec -> vm.actionNavToFullRecipe(rec) },
                checkAction = {id -> vm.actionCheckRecipe(id) },
                switchEditMode = { vm.actionSwitchEditMode() },
                actionEdit = { id -> vm.actionEdit(id) },
                actionRecipeToNextStep = { id ->
                    vm.actionRecipeToNextStep(id)
                    Log.i("tttttttttttttttt", "tttttttttt")
                                         },
                getCheckState = { id -> vm.getCheckState(id)}
            )
        }
    }
}