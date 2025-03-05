package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.event.AddTagEvent
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.state.AddTagUIState

@Composable
fun AddTag(
    state: AddTagUIState,
    onEvent: (AddTagEvent) -> Unit,
) {
    val isError: MutableState<Boolean> = remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .padding(24.dp)) {
        TextTitleItalic(
            text = stringResource(R.string.create_tag),
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        TagName(state, isError)
        Spacer(modifier = Modifier.height(24.dp))
        ChooseCategoryTag(state, onEvent)
        Spacer(modifier = Modifier.height(36.dp))
        DoneButton(
            text = stringResource(id = R.string.add),
            modifier = Modifier
        ) {
            if (state.text.value != "" && state.category.value != null) {
                onEvent(AddTagEvent.Done)
            } else {
                isError.value = true
            }
        }
    }
}

@Composable
private fun ChooseCategoryTag(
    state: AddTagUIState,
    onEvent: (AddTagEvent) -> Unit
) {
    TextBody(
        text = stringResource(R.string.category),
        modifier = Modifier
            .padding(start = 12.dp)
            .padding(bottom = 12.dp),
        textAlign = TextAlign.Start
    )
    CommonButton(
        if (state.category.value?.name == "") stringResource(R.string.select_category) else state.category.value?.name
            ?: "",
        image = R.drawable.search,
    ) {
        onEvent(AddTagEvent.ChooseCategory)
    }
}

@Composable
private fun TagName(
    state: AddTagUIState,
    isError: MutableState<Boolean>
) {
    TextBody(
        text = stringResource(R.string.tag_name),
        modifier = Modifier
            .padding(start = 12.dp)
            .padding(bottom = 12.dp),
        textAlign = TextAlign.Start
    )
    EditTextLine(
        state.text,
        stringResource(R.string.enter_tag_name_here),
        modifier = Modifier, isRequired = true, isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTag() {
    WeekOnAPlateTheme {
        val state = AddTagUIState()
        AddTag(state) {}
    }
}