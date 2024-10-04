package week.on.a.plate.screenSearchCategories.view

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.CreateTagOrIngredient
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.core.uitools.TagBig
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.screenSearchCategories.event.CategoriesSearchEvent
import week.on.a.plate.screenSearchCategories.logic.CategoriesSearchViewModel

@Composable
fun CategoriesSearchMain(vm: CategoriesSearchViewModel) {
    val state = vm.state
    val onEventCategoriesSearch = { event: CategoriesSearchEvent ->
        vm.onEvent(event)
    }
    vm.state.allTags = vm.allTagCategories.collectAsState()
    vm.state.allIngredients = vm.allIngredientCategories.collectAsState()

    val list = if (vm.isTag) {
        state.allTags.value.map { it.name }
    } else {
        state.allIngredients.value.map { it.name }
    }

    CategoriesSearch(
        list,
        state.searchText,
        state.resultSearch,
        searchEvent = { onEventCategoriesSearch(CategoriesSearchEvent.Search) },
        voiceSearchEvent = { onEventCategoriesSearch(CategoriesSearchEvent.VoiceSearch) },
        createAction = { onEventCategoriesSearch(CategoriesSearchEvent.Create(it)) },
        done = { onEventCategoriesSearch(CategoriesSearchEvent.Select(it)) },
        close = { onEventCategoriesSearch(CategoriesSearchEvent.Close) },
        editOrDelete = { onEventCategoriesSearch(CategoriesSearchEvent.EditOrDelete(it)) },
    )
}


@Composable
fun CategoriesSearch(
    allCategoriesNames: List<String>,
    searchText: MutableState<String>,
    resultSearch: MutableState<List<String>>,
    searchEvent: () -> Unit,
    voiceSearchEvent: () -> Unit,
    createAction: (String) -> Unit,
    done: (String) -> Unit,
    close: () -> Unit,
    editOrDelete: (String) -> Unit,
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
                        createAction(searchText.value)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                items(resultSearch.value.size) {
                    TagBig(resultSearch.value[it], ColorButtonNegativeGrey, clickable = {
                        done(resultSearch.value[it])
                    }, longClick = {
                        editOrDelete(resultSearch.value[it])
                    })
                    Spacer(modifier = Modifier.height(24.dp))
                }
            } else {
                items(allCategoriesNames.size) {
                    TagBig(allCategoriesNames[it], ColorButtonNegativeGrey,
                        clickable = {
                        done(allCategoriesNames[it])
                    }, longClick = {
                        editOrDelete(allCategoriesNames[it])
                    })
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
    Row(
        Modifier
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(24.dp))
        BackButtonOutlined { close() }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = searchText,
            actionSearch = { s -> searchEvent() },
            actionSearchVoice = { voiceSearchEvent() }, actionClear = { searchEvent() })
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
            mutableStateOf(tags.map { it -> it.name })
        }
        CategoriesSearch(
            tags.map { it -> it.name },
            search,
            result,
            {}, {}, {}, {}, {},{}
        )
    }
}
