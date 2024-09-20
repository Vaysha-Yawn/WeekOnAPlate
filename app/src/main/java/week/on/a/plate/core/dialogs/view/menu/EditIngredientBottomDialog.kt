package week.on.a.plate.core.dialogs.view.menu

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
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionIngredientExample
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.dialogs.data.DialogData
import week.on.a.plate.core.uitools.EditNumberLine
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.uitools.ingredientCard.CardIngredient
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddIngredientBottomDialogContent(
    data: DialogData.AddIngredientPosition, onEvent: (MainEvent) -> Unit
) {
    EditOrAddIngredientBottomDialogContent(
        stringResource(data.doneBtnText),
        data.ingredientState,
        data.text,
        data.count,
        onEvent, data.done, data.chooseIngredient
    )
}


@Composable
fun EditIngredientBottomDialogContent(
    data: DialogData.EditIngredient, onEvent: (MainEvent) -> Unit
) {
    EditOrAddIngredientBottomDialogContent(
        stringResource(data.doneBtnText),
        data.ingredientState,
        data.text,
        data.count,
        onEvent, data.done, data.chooseIngredient
    )
}


@Composable
fun EditOrAddIngredientBottomDialogContent(
    doneText: String,
    ingredientState: MutableState<IngredientView?>,
    description: MutableState<String>,
    count: MutableDoubleState,
    onEvent: (MainEvent) -> Unit,
    done: () -> Unit,
    chooseIngredient: () -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 24.dp)) {
        TextBody(
            text = stringResource(R.string.Ingredient),
            modifier = Modifier.padding(horizontal = 48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (ingredientState.value != null) {
            CardIngredient(ingredient = ingredientState.value!!, {
                Image(
                    painter = painterResource(id = R.drawable.find_replace),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }, chooseIngredient)
        } else {
            CommonButton(
                stringResource(R.string.Specify_ingredient),
                R.drawable.search,
                chooseIngredient
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        EditTextLine(
            description,
            stringResource(R.string.description),
            stringResource(R.string.Pieces), modifier = Modifier.padding(horizontal = 24.dp)
        ) { value ->
            description.value = value
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            EditNumberLine(
                count,
                stringResource(R.string.amount) + "," + (ingredientState.value?.measure ?: ""),
                "0.0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) { newnum ->
                count.doubleValue = newnum
            }
        }
        val messageError = stringResource(id = R.string.message_select_ingredient)
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = doneText, modifier = Modifier.padding(horizontal = 24.dp)) {
            if (ingredientState.value != null) {
                done()
            } else {
                onEvent(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewEditIngredientBottomDialog() {
    WeekOnAPlateTheme {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        EditIngredientBottomDialogContent(
            DialogData.EditIngredient(
                positionIngredientExample,
                stateBottom
            ) {}, {})
    }
}