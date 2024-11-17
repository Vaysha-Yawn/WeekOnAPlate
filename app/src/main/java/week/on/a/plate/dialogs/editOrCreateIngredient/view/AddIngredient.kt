package week.on.a.plate.dialogs.editOrCreateIngredient.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.editOrCreateIngredient.event.AddIngredientEvent
import week.on.a.plate.dialogs.editOrCreateIngredient.state.AddIngredientUIState

@Composable
fun AddIngredient(
    state: AddIngredientUIState,
    onEvent: (AddIngredientEvent) -> Unit,
) {
    val isError: MutableState<Boolean> = remember { mutableStateOf(false) }
    LazyColumn(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .padding(24.dp)) {
        item {
            TextTitleItalic(
                text = stringResource(R.string.create_ingredient),
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(36.dp))
            TextBody(
                text = stringResource(R.string.ingredient_name),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            EditTextLine(
                state.name,
                stringResource(R.string.enter_ingredient_name),
                modifier = Modifier, isRequired = true, isError = isError
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier,
                horizontalArrangement = Arrangement.End,
            ) {
                TextBody(
                    text = stringResource(R.string.is_this_a_liquid),
                    modifier = Modifier
                        .padding(start = 12.dp), textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(24.dp))
                Checkbox(
                    checked = state.isLiquid.value,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary,
                        checkmarkColor = MaterialTheme.colorScheme.onBackground
                    ),
                    onCheckedChange = {
                        if (state.isLiquid.value) {
                            state.isLiquid.value = false
                        } else {
                            state.isLiquid.value = true
                        }
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))

            TextBody(
                text = stringResource(R.string.category),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            CommonButton(
                if (state.category.value == null) stringResource(R.string.select_category) else state.category.value!!.name,
                image = R.drawable.search
            ) {
                onEvent(AddIngredientEvent.ChooseCategory)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextBody(
                text = "Фото",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(bottom = 12.dp), textAlign = TextAlign.Start
            )
            EditTextLine(
                state.photoUri,
                stringResource(R.string.enter_image_link),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(24.dp))
            DoneButton(
                text = stringResource(id = R.string.add),
                modifier = Modifier
            ) {
                if (state.name.value != "" && state.category.value != null) {
                    onEvent(AddIngredientEvent.Done)
                } else {
                    isError.value = true
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddIngredient() {
    WeekOnAPlateTheme {
        val state = AddIngredientUIState("", false, null, "")
        AddIngredient( state) {}
    }
}