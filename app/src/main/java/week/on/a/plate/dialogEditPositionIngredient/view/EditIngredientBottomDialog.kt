package week.on.a.plate.dialogEditPositionIngredient.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditNumberLine
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.uitools.ingredientCard.CardIngredient
import week.on.a.plate.data.dataView.example.positionIngredientExample
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.dialogEditPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.dialogEditPositionIngredient.state.EditPositionIngredientUIState

@Composable
fun EditOrAddIngredientBottomDialogContent(
    state: EditPositionIngredientUIState,
    onEvent: (EditPositionIngredientEvent) -> Unit,
    onEventMain: (MainEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 24.dp)
    ) {
        TextBody(
            text = stringResource(R.string.Ingredient),
            modifier = Modifier.padding(horizontal = 36.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (state.ingredientState.value != null) {
            CardIngredient(
                ingredient = state.ingredientState.value!!,
                {
                    Image(
                        painter = painterResource(id = R.drawable.find_replace),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                },
            ) {
                onEvent(EditPositionIngredientEvent.ChooseIngredient)
            }
        } else {
            CommonButton(
                stringResource(R.string.Specify_ingredient),
                image = R.drawable.search, modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                onEvent(EditPositionIngredientEvent.ChooseIngredient)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(
            text = "Описание",
            modifier = Modifier.padding(horizontal = 36.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        EditTextLine(
            state.description,
            stringResource(R.string.Pieces),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) { value ->
            state.description.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(
            text = "Колличество" + if (state.ingredientState.value != null) {
                ", " + state.ingredientState.value!!.measure
            } else "",
            modifier = Modifier.padding(horizontal = 36.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            EditNumberLine(
                state.count,
                "0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) { newnum ->
                state.count.intValue = newnum
            }
        }
        val messageError = stringResource(id = R.string.message_select_ingredient)
        Spacer(modifier = Modifier.height(36.dp))
        DoneButton(
            text = stringResource(id = R.string.apply),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            if (state.ingredientState.value != null) {
                onEvent(EditPositionIngredientEvent.Done)
            } else {
                onEventMain(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewEditIngredientBottomDialog() {
    WeekOnAPlateTheme {
        val state = rememberModalBottomSheetState(true)
        EditOrAddIngredientBottomDialogContent(
            EditPositionIngredientUIState(positionIngredientExample), {}
        ) {}
    }
}