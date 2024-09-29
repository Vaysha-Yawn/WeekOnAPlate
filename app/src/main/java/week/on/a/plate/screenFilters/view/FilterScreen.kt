package week.on.a.plate.screenFilters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.core.uitools.CreateTagOrIngredient
import week.on.a.plate.core.uitools.TagBig
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterUIState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.screenFilters.state.FilterMode

@Composable
fun FilterScreen(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    Scaffold(Modifier.fillMaxSize(), floatingActionButton = {
        CommonButton( modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(R.string.selected_tags),
        ) {
            onEvent(FilterEvent.SelectedFilters)
        }
    }, floatingActionButtonPosition = FabPosition.Center) { innerPadding ->
        Column {
            when(stateUI.filterMode.value){
                FilterMode.All -> {
                    TabRowFilter(
                        stateUI.activeFilterTabIndex,
                        stateUI.selectedTags.value.size,
                        stateUI.selectedIngredients.value.size
                    )
                }
                FilterMode.OneIngredient -> {stateUI.activeFilterTabIndex.intValue = 1}
                FilterMode.TagList -> {stateUI.activeFilterTabIndex.intValue = 0}
            }
            LazyColumn(
                Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 24.dp).padding(bottom = 110.dp)
            ) {
                when (stateUI.activeFilterTabIndex.intValue) {
                    0 -> {
                        if (stateUI.filtersSearchText.value != "") {
                            item {
                                Spacer(modifier = Modifier.height(24.dp))
                                CreateTagOrIngredient(stateUI.filtersSearchText.value) {
                                    onEvent(FilterEvent.CreateTag(stateUI.filtersSearchText.value))
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                            items(stateUI.resultSearchFilterTags.value.size) {
                                TagBig(
                                    tag = stateUI.resultSearchFilterTags.value[it], isActive =
                                    stateUI.selectedTags.value.contains(stateUI.resultSearchFilterTags.value[it])
                                ){
                                    onEvent(FilterEvent.SelectTag(stateUI.resultSearchFilterTags.value[it]))
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        } else {
                            items(stateUI.allTagsCategories.value.size) {
                                CategoriesTags(
                                    stateUI.allTagsCategories.value[it],
                                    stateUI.selectedTags, onEvent
                                )
                            }
                        }
                    }

                    1 -> {
                        if (stateUI.filtersSearchText.value != "") {
                            item {
                                Spacer(modifier = Modifier.height(24.dp))
                                CreateTagOrIngredient(stateUI.filtersSearchText.value) {
                                    onEvent(FilterEvent.CreateIngredient(stateUI.filtersSearchText.value))
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                            items(stateUI.resultSearchFilterIngredients.value.size) {
                                TagBig(
                                    ingredientView = stateUI.resultSearchFilterIngredients.value[it],
                                    isActive =
                                    stateUI.selectedIngredients.value.contains(stateUI.resultSearchFilterIngredients.value[it])
                                ){
                                    onEvent(FilterEvent.SelectIngredient(stateUI.resultSearchFilterIngredients.value[it]))
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        } else {
                            items(stateUI.allIngredientsCategories.value.size) {
                                IngredientCategories(
                                    stateUI.allIngredientsCategories.value[it],
                                    stateUI.selectedIngredients, onEvent
                                )
                            }
                        }
                    }
                }

            }
        }
        innerPadding
    }
}

@Composable
fun TabRowFilter(
    activeFilterTabIndex: MutableIntState,
    selectedTagsSize: Int,
    selectedIngredientsSize: Int
) {
    val tabTitles = listOf("Тэги", "Ингредиенты")
    TabRow(
        selectedTabIndex = activeFilterTabIndex.intValue,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[activeFilterTabIndex.intValue]),
                color = MaterialTheme.colorScheme.onBackground,
                height = 2.dp
            )
        }) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextBody(
                            text =
                            if (title == "Тэги") selectedTagsSize.toString()
                            else selectedIngredientsSize.toString()
                        )
                        TextBody(text = title)
                    }
                },
                selected = activeFilterTabIndex.intValue == index,
                onClick = { activeFilterTabIndex.intValue = index }
            )
        }
    }
}

@Composable
fun CategoriesTags(
    tagCategoryView: TagCategoryView,
    selectedTags: MutableState<List<RecipeTagView>>,
    onEvent: (FilterEvent) -> Unit,
) {
    Spacer(modifier = Modifier.height(12.dp))
    TextTitle(text = tagCategoryView.name)
    Spacer(modifier = Modifier.height(12.dp))
    for (tag in tagCategoryView.tags) {
        TagBig(tag = tag, selectedTags.value.contains(tag)){
            onEvent(FilterEvent.SelectTag(tag))
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun IngredientCategories(
    ingredientCategory: IngredientCategoryView,
    selectedIngredients: MutableState<List<IngredientView>>,
    onEvent: (FilterEvent) -> Unit,

    ) {
    Spacer(modifier = Modifier.height(12.dp))
    TextTitle(text = ingredientCategory.name)
    Spacer(modifier = Modifier.height(12.dp))
    for (ingredient in ingredientCategory.ingredientViews) {
        TagBig(ingredientView = ingredient, selectedIngredients.value.contains(ingredient)){
            onEvent(FilterEvent.SelectIngredient(ingredient))
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    WeekOnAPlateTheme {
        FilterScreen(FilterUIState().apply {
            filtersSearchText.value = "По"
            allTagsCategories.value = tags
            allIngredientsCategories.value = ingredients
            activeFilterTabIndex.intValue = 1
            selectedTags.value = selectedTags.value.toMutableList().apply {
                tags.map { it -> it.tags }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
            selectedIngredients.value = selectedIngredients.value.toMutableList().apply {
                ingredients.map { it -> it.ingredientViews }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }

            resultSearchFilterTags.value = mutableListOf<RecipeTagView>().toMutableList().apply {
                tags.map { it -> it.tags }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
            resultSearchFilterIngredients.value = mutableListOf<IngredientView>().apply {
                ingredients.map { it -> it.ingredientViews }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
        }) {

        }
    }
}
