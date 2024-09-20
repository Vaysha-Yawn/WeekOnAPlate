package week.on.a.plate.core.dialogs.menu.editRecipePosition.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.dialogs.menu.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun EditRecipePositionDialogContent(onEvent: (EditRecipePositionEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        ButtonRow(
            R.drawable.add_shopping_cart,
            stringResource(R.string.add_shopping_cart),
        ) {
            onEvent(EditRecipePositionEvent.AddToCart)
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ) {
            onEvent(EditRecipePositionEvent.Double)
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ) {
            onEvent(EditRecipePositionEvent.Delete)
        }

        ButtonRow(
            R.drawable.find_replace,
            stringResource(R.string.change_recipe),
        ) {
            onEvent(EditRecipePositionEvent.FindReplace)
        }

        ButtonRow(
            R.drawable.numbers,
            stringResource(R.string.Change_number_of_servings)
        ) {
            onEvent(EditRecipePositionEvent.ChangePotionsCount)
        }

        ButtonRow(
            R.drawable.back_key,
            stringResource(R.string.move),
        ) {
            onEvent(EditRecipePositionEvent.Move)
        }
    }
}

@Composable
fun ButtonRow(imgRec: Int, text: String, event: () -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { event() }) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
        )
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun PreviewEditRecipePanel() {
    WeekOnAPlateTheme {
        EditRecipePositionDialogContent() {}
    }
}