package week.on.a.plate.core.dialogs.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.uitools.CreateTagOrIngredient
import week.on.a.plate.core.uitools.TagBig
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.search.view.viewTools.SearchLine
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun CategoriesSearch(
    allCategoriesNames: List<String>,
    searchText: MutableState<String>,
    resultSearch: MutableState<List<String>>,
    searchEvent: () -> Unit,
    voiceSearchEvent: () -> Unit,
    createAction: () -> Unit,
    done: (String) -> Unit,
    close: () -> Unit
) {
    Column {
        TopSearchPanelCategory(searchText, searchEvent, voiceSearchEvent, close)
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
        ) {
            if (searchText.value != "") {
                item {
                    CreateTagOrIngredient(searchText.value) {
                        createAction()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                items(resultSearch.value.size) {
                    TagBig(resultSearch.value[it], ColorButtonNegativeGrey){
                        done(resultSearch.value[it])
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            } else {
                items(allCategoriesNames.size) {
                    TagBig(allCategoriesNames[it], ColorButtonNegativeGrey){
                        done(allCategoriesNames[it])
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}


@Composable
fun TopSearchPanelCategory(
    searchText: MutableState<String>,
    searchEvent: () -> Unit,
    voiceSearchEvent: () -> Unit,
    close: () -> Unit,
) {
    Row(Modifier.padding(24.dp).background(MaterialTheme.colorScheme.surface), verticalAlignment = Alignment.CenterVertically) {
        BackButtonOutlined { close() }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = searchText,
            actionSearch = { s -> searchEvent() },
            actionSearchVoice = { voiceSearchEvent() })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    WeekOnAPlateTheme {
        val search = remember {
            mutableStateOf("По")
        }
        val result = remember {
            mutableStateOf(tags.map { it->it.name })
        }
        CategoriesSearch(
            tags.map { it->it.name },
            search,
            result,
            {},{},{},{},{}
        )
    }
}
