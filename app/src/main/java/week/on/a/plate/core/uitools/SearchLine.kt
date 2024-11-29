package week.on.a.plate.core.uitools

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorStrokeGrey
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.filters.view.clickNoRipple


@Composable
fun SearchLine(
    textSearch: MutableState<String>,
    modifier: Modifier = Modifier,
    actionSearch: (s: String) -> Unit,
    actionSearchVoice: () -> Unit,
    actionClear: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = textSearch.value,
        textStyle = Typography.bodyMedium,
        onValueChange = { value: String ->
            textSearch.value = if (value.length<2){value}else{
                value[0].uppercaseChar()+value.substring(1 until value.length)
            }
            actionSearch(value)},
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.mic),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickNoRipple {
                        actionSearchVoice()
                    }
            )

        },
        trailingIcon = {
            if (textSearch.value!= ""){
                Image(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp).clickNoRipple {
                        textSearch.value = ""
                        actionClear()
                    }
                )
            }
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
            SearchLine(text, actionSearchVoice = {}, actionSearch = {}){}
        }

    }
}