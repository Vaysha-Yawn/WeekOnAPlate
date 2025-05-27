package week.on.a.plate.dialogs.editOtherPositionMoreDialog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ButtonRow
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.BaseContextProvider

@Composable
fun EditOtherPositionDialogContent( isForIngredient:Boolean, onEvent: (OtherPositionMoreEvent) -> Unit) {
    val contextProvider = BaseContextProvider(LocalContext.current)
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .padding(20.dp)) {
        ButtonRow(
            R.drawable.edit,
            text=stringResource(R.string.edit),
        ){
            onEvent(OtherPositionMoreEvent.Edit)

        }

        if (isForIngredient){
            ButtonRow(
                R.drawable.add_shopping_cart,
                text=stringResource(R.string.add_shopping_cart),
            ){
                onEvent(OtherPositionMoreEvent.AddToShopList(contextProvider))
            }
        }

        ButtonRow(
            R.drawable.back_key,
            text=stringResource(R.string.move),
        ){
            onEvent(OtherPositionMoreEvent.Move)
        }

        ButtonRow(
            R.drawable.add,
            text=stringResource(R.string.doubleR),
        ){
            onEvent(OtherPositionMoreEvent.Double)
        }

        ButtonRow(
            R.drawable.delete,
            text=stringResource(R.string.delete),
        ){
            onEvent(OtherPositionMoreEvent.Delete)
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