package week.on.a.plate.core.uitools

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.uitools.animate.AnimateErrorBox

@Composable
fun EditTextLine(
    text: MutableState<String>,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: MutableState<Boolean> = mutableStateOf(false),
    isRequired: Boolean = false, onTextEdit: () -> Unit = {}
) {
    AnimateErrorBox(isError) {
        OutlinedTextField(
            value = text.value,
            onValueChange = { newValue ->
                text.value = if (newValue.length < 2) {
                    newValue
                } else {
                    newValue[0].uppercaseChar() + newValue.substring(1 until newValue.length)
                }
                if (isRequired && newValue == "") {
                    isError.value = true
                }
                onTextEdit()
            },
            modifier = modifier.fillMaxWidth(),
            textStyle = Typography.bodyMedium,
            placeholder = {
                TextBodyDisActive(text = placeholder)
            },
            supportingText = {
                if (isRequired && text.value == "") {
                    TextSmall(
                        text = stringResource(R.string.necessarily),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            singleLine = false,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedContainerColor = MaterialTheme.colorScheme.background,

                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
            )
        )
    }
}

@Composable
fun EditNumberLine(
    num: MutableState<Int>,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val text = remember { mutableStateOf(if (num.value == 0) "" else num.value.toString()) }
    OutlinedTextField(
        value = text.value,
        onValueChange = { newValue ->
            text.value = newValue
            val d = newValue.toIntOrNull()
            num.value = d ?: 0
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
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
    )
}