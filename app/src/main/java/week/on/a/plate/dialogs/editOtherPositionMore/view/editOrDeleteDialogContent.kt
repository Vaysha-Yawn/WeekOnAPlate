package week.on.a.plate.dialogs.editOtherPositionMore.view

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
import week.on.a.plate.dialogs.editOtherPositionMore.event.EditOtherPositionEvent
import week.on.a.plate.core.uitools.ButtonRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun EditOtherPositionDialogContent( isForIngredient:Boolean, onEvent: (EditOtherPositionEvent) -> Unit) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(20.dp)) {
        ButtonRow(
            R.drawable.edit,
            text=stringResource(R.string.edit),
        ){
            onEvent(EditOtherPositionEvent.Edit)

        }

        if (isForIngredient){
            ButtonRow(
                R.drawable.add_shopping_cart,
                text=stringResource(R.string.add_shopping_cart),
            ){
                onEvent(EditOtherPositionEvent.AddToShopList)
            }
        }

        ButtonRow(
            R.drawable.back_key,
            text=stringResource(R.string.move),
        ){
            onEvent(EditOtherPositionEvent.Move)
        }

        ButtonRow(
            R.drawable.add,
            text=stringResource(R.string.doubleR),
        ){
            onEvent(EditOtherPositionEvent.Double)
        }

        ButtonRow(
            R.drawable.delete,
            text=stringResource(R.string.delete),
        ){
            onEvent(EditOtherPositionEvent.Delete)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOtherPanel() {
    WeekOnAPlateTheme {
        EditOtherPositionDialogContent(true) {}
    }
}