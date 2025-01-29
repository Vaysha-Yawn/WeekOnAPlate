package week.on.a.plate.dialogs.editIngredientInMenu.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditNumberLine
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.animate.AnimateErrorBox
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.data.dataView.example.positionIngredientExample
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.state.EditPositionIngredientUIState

@Composable
fun EditOrAddIngredientBottomDialogContent(
    state: EditPositionIngredientUIState,
    onEvent: (EditPositionIngredientEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 24.dp)
    ) {
        IngredientName(state)
        Spacer(modifier = Modifier.height(12.dp))
        Description(state)
        Count(state)
        Spacer(modifier = Modifier.height(36.dp))
        DoneButton(
            text = stringResource(id = R.string.apply),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            onEvent(EditPositionIngredientEvent.Done)
        }
    }
}

@Composable
private fun Count(state: EditPositionIngredientUIState) {
    TextBody(
        text = stringResource(R.string.count) + if (state.ingredientState.value != null) {
            (", " + state.ingredientState.value?.measure) ?: ""
        } else "",
        modifier = Modifier.padding(horizontal = 36.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    EditNumberLine(
        state.count,
        "0",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}

@Composable
private fun Description(state: EditPositionIngredientUIState) {
    TextBody(
        text = stringResource(R.string.description_title),
        modifier = Modifier.padding(horizontal = 36.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(
        state.description,
        stringResource(R.string.Pieces),
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
private fun IngredientName(state: EditPositionIngredientUIState) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle(state.ingredientState.value?.name?:"", Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(12.dp))
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.outline)
}


@Preview(showBackground = true)
@Composable
fun PreviewEditIngredientBottomDialog() {
    WeekOnAPlateTheme {
        EditOrAddIngredientBottomDialogContent(
            EditPositionIngredientUIState(positionIngredientExample)
        ) {}
    }
}