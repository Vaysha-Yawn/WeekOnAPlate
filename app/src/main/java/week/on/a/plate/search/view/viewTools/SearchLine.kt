package week.on.a.plate.search.view.viewTools

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
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
import week.on.a.plate.ui.theme.ColorBackgroundWhite
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.ColorTransparent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLine(
    textSearch: MutableState<String>,
    modifier: Modifier = Modifier, actionSearch: (s: String) -> Unit,
    actionSearchVoice: () -> Unit,
) {
    val expand = remember{ mutableStateOf(false)}
    val onActiveChange: (Boolean) -> Unit = { bool ->
        expand.value = bool
    }
    val colors1 = SearchBarDefaults.colors(
        containerColor = ColorBackgroundWhite,
        dividerColor = ColorTransparent,
        inputFieldColors = TextFieldDefaults.colors()
    )
    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = textSearch.value,
                onQueryChange = { str ->
                    textSearch.value = str
                },
                onSearch = { text -> actionSearch(text) },
                expanded = expand.value,
                onExpandedChange = onActiveChange,
                placeholder = {
                    TextBodyDisActive(text = stringResource(R.string.search_placeholder))
                },
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
                colors = colors1.inputFieldColors,
            )
        },
        expanded = expand.value,
        onExpandedChange = onActiveChange,
        modifier = modifier.border(1.dp, ColorStrokeGrey, RoundedCornerShape(50.dp)),
        colors = colors1,
    ){}
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