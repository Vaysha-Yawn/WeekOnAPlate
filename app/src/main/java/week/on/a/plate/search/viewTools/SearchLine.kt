package week.on.a.plate.search.viewTools

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.ui.theme.ColorBackgroundWhite
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.ColorTransparent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


// todo доделать под нужды модуля
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLine(
    textSearch: MutableState<String>, active: MutableState<Boolean>,
    modifier: Modifier = Modifier, actionSearch: (s: String) -> Unit,
    actionSearchVoice: () -> Unit,
) {
    DockedSearchBar(query = textSearch.value,
        onQueryChange = { str ->
            textSearch.value = str
        },
        onSearch = { text -> actionSearch(text) },
        active = active.value,
        onActiveChange = { bool ->
            active.value = bool
        },
        modifier = modifier.border(1.dp, ColorStrokeGrey, RoundedCornerShape(50.dp)),
        placeholder = {
            TextBodyDisActive(text = "Что ищем сегодня?")
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
                        if (active.value) actionSearchVoice()
                    }
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = ColorBackgroundWhite,
            dividerColor = ColorTransparent,
            inputFieldColors = TextFieldDefaults.colors()
        )
    ) {
        //todo в поиске тэгов тут можно положить ответы
        // а в поиске рецептов ответ с рецептами
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLine() {
    WeekOnAPlateTheme {
        val text = remember {
            mutableStateOf("Tercn")
        }
        val active = remember {
            mutableStateOf(false)
        }
        Column {
            SearchLine(text, active, actionSearchVoice = {}, actionSearch = {})
        }

    }
}