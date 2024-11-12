package week.on.a.plate.dialogs.cookStepMore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.ButtonRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent

@Composable
fun CookStepMoreContent( onEvent: (CookStepMoreEvent) -> Unit) {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .padding(20.dp)) {
        ButtonRow(
            R.drawable.start,
            R.drawable.edit,
            text= stringResource(R.string.change_start_recipe_time),
        ){
            onEvent(CookStepMoreEvent.ChangeStartRecipeTime)

        }

        ButtonRow(
            R.drawable.end,
            R.drawable.edit,
            text= stringResource(R.string.change_end_recipe_time),
        ){
            onEvent(CookStepMoreEvent.ChangeEndRecipeTime)
        }

        ButtonRow(
            R.drawable.add_time,
            text= stringResource(R.string.increase_step_time),
        ){
            onEvent(CookStepMoreEvent.IncreaseStepTime)
        }

        ButtonRow(
            R.drawable.next,
            text= stringResource(R.string.move_step_by_time_start),
        ){
            onEvent(CookStepMoreEvent.MoveStepByTimeStart)
        }

        ButtonRow(
            R.drawable.numbers,
            text = stringResource(R.string.Change_number_of_servings),
        ){
            onEvent(CookStepMoreEvent.ChangePortionsCount)
        }

        ButtonRow(
            R.drawable.delete,
            text= stringResource(R.string.delete),
        ){
            onEvent(CookStepMoreEvent.Delete)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOtherPanel() {
    WeekOnAPlateTheme {
        CookStepMoreContent {}
    }
}