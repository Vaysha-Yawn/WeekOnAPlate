package week.on.a.plate.search.view.viewTools

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


@Composable
fun SearchLine(
    textSearch: MutableState<String>,
    modifier: Modifier = Modifier, actionSearch: (s: String) -> Unit,
    actionSearchVoice: () -> Unit,
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = textSearch.value,
        onValueChange = { value: String -> textSearch.value = value },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.mic),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        actionSearchVoice()
                    }
            )
        },
        placeholder = {
            TextBodyDisActive(text = stringResource(id = R.string.search_placeholder))
        },
        shape = RoundedCornerShape(50.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorStrokeGrey,
            unfocusedBorderColor = ColorStrokeGrey,

            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        ),
        keyboardActions = KeyboardActions {
            actionSearch(textSearch.value)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLine() {
    WeekOnAPlateTheme {
        val text = remember {
            mutableStateOf("Tercn")
        }
        Column {
            SearchLine(text, actionSearchVoice = {}, actionSearch = {})
        }

    }
}