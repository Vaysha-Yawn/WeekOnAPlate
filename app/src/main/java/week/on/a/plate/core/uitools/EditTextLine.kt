package week.on.a.plate.core.uitools

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorPanelLightGrey
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.Typography

@Composable
fun EditTextLine(
    text: MutableState<String>,
    placeholder: String,
    modifier : Modifier = Modifier,
) {
    OutlinedTextField(
        value = text.value,
        onValueChange = { newValue ->
            text.value = if (newValue.length<2){newValue}else{
                newValue[0].uppercaseChar()+newValue.substring(1 until newValue.length)
            }
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = Typography.bodyMedium,
        placeholder = {
            TextBodyDisActive(text = placeholder)
        },
        singleLine = false,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            unfocusedContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            disabledContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.outline,
        )
    )
}

@Composable
fun EditNumberLine(
    num: MutableState<Int>,
    placeholder: String,
    modifier: Modifier = Modifier,
    textChangeEvent: (Int) -> Unit,
) {
    OutlinedTextField(
        value = num.value.toString(),
        onValueChange = { newValue ->
            val d = newValue.toIntOrNull()
            if (d != null) {
                textChangeEvent(d)
            }
        },
        modifier = modifier,
        textStyle = Typography.bodyMedium,
        placeholder = {
            TextBodyDisActive(text = placeholder)
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            unfocusedContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            disabledContainerColor = if (isSystemInDarkTheme()) ColorTextBlack else ColorPanelLightGrey,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.outline,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
    )
}