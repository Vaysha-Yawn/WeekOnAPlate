package week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.view

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
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ButtonRow
import week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.event.ActionMoreRecipePositionEvent

@Composable
fun EditRecipePositionDialogContent(onEvent: (ActionMoreRecipePositionEvent) -> Unit) {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .padding(20.dp)) {
        ButtonRow(
            R.drawable.cook_cap,
            text= stringResource(R.string.plan_your_cooking),
        ) {
            onEvent(ActionMoreRecipePositionEvent.CookPlan)
        }

        ButtonRow(
            R.drawable.add_shopping_cart,
            text= stringResource(R.string.add_shopping_cart),
        ) {
            onEvent(ActionMoreRecipePositionEvent.AddToCart)
        }

        ButtonRow(
            R.drawable.add,
            text= stringResource(R.string.doubleR),
        ) {
            onEvent(ActionMoreRecipePositionEvent.Double)
        }

        ButtonRow(
            R.drawable.find_replace,
            text= stringResource(R.string.change_recipe),
        ) {
            onEvent(ActionMoreRecipePositionEvent.FindReplace)
        }

        ButtonRow(
            R.drawable.numbers,
            text= stringResource(R.string.Change_number_of_servings)
        ) {
            onEvent(ActionMoreRecipePositionEvent.ChangePotionsCount)
        }

        ButtonRow(
            R.drawable.back_key,
            text= stringResource(R.string.move),
        ) {
            onEvent(ActionMoreRecipePositionEvent.Move)
        }

        ButtonRow(
            R.drawable.delete,
            text= stringResource(R.string.delete),
        ) {
            onEvent(ActionMoreRecipePositionEvent.Delete)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditRecipePanel() {
    WeekOnAPlateTheme {
        EditRecipePositionDialogContent() {}
    }
}