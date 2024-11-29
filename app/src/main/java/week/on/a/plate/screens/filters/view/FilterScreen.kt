package week.on.a.plate.screens.filters.view

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.CreateTagOrIngredient
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.filters.state.FilterUIState

@Composable
fun FilterScreen(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
    ) {
        if (stateUI.filterEnum.value == FilterEnum.IngredientAndTag) {
            TabRowFilter(
                stateUI.activeFilterTabIndex,
                stateUI.selectedTags.value.size,
                stateUI.selectedIngredients.value.size
            )
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {
            if (stateUI.searchText.value != "") {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    if (checkNotHaveFilter(stateUI)) {
                        CreateTagOrIngredient(stateUI.searchText.value) {
                            onEvent(FilterEvent.CreateActive)
                        }
                    }
                }
            }

            if ((stateUI.activeFilterTabIndex.intValue == 0 && stateUI.filterEnum.value == FilterEnum.IngredientAndTag)
                || stateUI.filterEnum.value == FilterEnum.Tag
            ) {
                if (stateUI.searchText.value != "") {
                    items(stateUI.resultSearchTags.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))

                        FilterItemWithMore(stateUI.resultSearchTags.value[it].tagName,
                            stateUI.selectedIngredients.value.contains(stateUI.resultSearchIngredients.value[it]),
                            false,
                            click = { onEvent(FilterEvent.SelectTag(stateUI.resultSearchTags.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteTag(stateUI.resultSearchTags.value[it])) }
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

            if ((stateUI.activeFilterTabIndex.intValue == 1 && stateUI.filterEnum.value == FilterEnum.IngredientAndTag)
                || stateUI.filterEnum.value == FilterEnum.Ingredient
            ) {
                if (stateUI.searchText.value != "") {
                    items(stateUI.resultSearchIngredients.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterItemWithMore(stateUI.resultSearchIngredients.value[it].name,
                            stateUI.selectedIngredients.value.contains(stateUI.resultSearchIngredients.value[it]),
                            true,
                            click = { onEvent(FilterEvent.SelectIngredient(stateUI.resultSearchIngredients.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteIngredient(stateUI.resultSearchIngredients.value[it])) }
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

            if (stateUI.filterEnum.value == FilterEnum.CategoryTag) {
                if (stateUI.searchText.value != "") {
                    items(stateUI.resultSearchTagsCategories.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterItemWithMore(stateUI.resultSearchTagsCategories.value[it].name,
                            stateUI.selectedTagsCategories.value.contains(stateUI.resultSearchTagsCategories.value[it]),
                            false,
                            click = { onEvent(FilterEvent.SelectTagCategory(stateUI.resultSearchTagsCategories.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteTagCategory(stateUI.resultSearchTagsCategories.value[it])) }
                    }
                } else {
                    items(stateUI.allTagsCategories.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterItemWithMore(stateUI.allTagsCategories.value[it].name,
                            stateUI.selectedTagsCategories.value.contains(stateUI.allTagsCategories.value[it]),
                            false,
                            click = { onEvent(FilterEvent.SelectTagCategory(stateUI.allTagsCategories.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteTagCategory(stateUI.allTagsCategories.value[it])) }
                    }
                }
            }

            if (stateUI.filterEnum.value == FilterEnum.CategoryIngredient) {
                if (stateUI.searchText.value != "") {
                    items(stateUI.resultSearchIngredientsCategories.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterItemWithMore(stateUI.resultSearchIngredientsCategories.value[it].name,
                            stateUI.selectedIngredientsCategories.value.contains(
                                stateUI.resultSearchIngredientsCategories.value[it]
                            ),
                            true,
                            click = { onEvent(FilterEvent.SelectIngredientCategory(stateUI.resultSearchIngredientsCategories.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteIngredientCategory(stateUI.resultSearchIngredientsCategories.value[it])) }
                    }
                } else {
                    items(stateUI.allIngredientsCategories.value.size) {
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterItemWithMore(stateUI.allIngredientsCategories.value[it].name,
                            stateUI.selectedIngredientsCategories.value.contains(stateUI.allIngredientsCategories.value[it]),
                            true,
                            click = { onEvent(FilterEvent.SelectIngredientCategory(stateUI.allIngredientsCategories.value[it])) })
                        { onEvent(FilterEvent.EditOrDeleteIngredientCategory(stateUI.allIngredientsCategories.value[it])) }
                    }
                }
            }
            item { 200.dp }
        }
        if (stateUI.filterMode.value == FilterMode.Multiple) {
            DoneButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp)
                    .padding(top = 6.dp),
                text = stringResource(R.string.apply),
            ) {
                onEvent(FilterEvent.Done)
            }
        }
    }
}


fun checkNotHaveFilter(stateUI: FilterUIState): Boolean {
    return (stateUI.resultSearchTags.value.find {
        //tag
        it.tagName.lowercase() == stateUI.searchText.value.trim()
            .lowercase()
    } == null
            && ((stateUI.filterMode.value == FilterMode.Multiple
            && stateUI.activeFilterTabIndex.intValue == 0)
            || stateUI.filterEnum.value == FilterEnum.Tag))

            //Ingredient
            || (stateUI.resultSearchIngredients.value.find {
        it.name.lowercase() == stateUI.searchText.value.trim()
            .lowercase()
    } == null && ((stateUI.filterMode.value == FilterMode.Multiple
            && stateUI.activeFilterTabIndex.intValue == 1)
            || stateUI.filterEnum.value == FilterEnum.Ingredient))

            //CategoryTag
            || (stateUI.resultSearchTagsCategories.value.find {
        it.name.lowercase() == stateUI.searchText.value.trim()
            .lowercase()
    } == null && stateUI.filterEnum.value == FilterEnum.CategoryTag)

            //CategoryIngredient
            || (stateUI.resultSearchIngredientsCategories.value.find {
        it.name.lowercase() == stateUI.searchText.value.trim()
            .lowercase()
    } == null && stateUI.filterEnum.value == FilterEnum.CategoryIngredient)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesTags(
    tagCategoryView: TagCategoryView,
    selectedTags: MutableState<List<RecipeTagView>>,
    onEvent: (FilterEvent) -> Unit,
) {
    Spacer(modifier = Modifier.height(12.dp))
    TextTitle(text = tagCategoryView.name)
    Spacer(modifier = Modifier.height(12.dp))
    FlowRow(Modifier.fillMaxWidth()) {
        for (tag in tagCategoryView.tags) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .padding(bottom = 12.dp)
            ) {
                FilterItemWithMore(tag.tagName, selectedTags.value.contains(tag), false,
                    click = { onEvent(FilterEvent.SelectTag(tag)) })
                { onEvent(FilterEvent.EditOrDeleteTag(tag)) }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientCategories(
    ingredientCategory: IngredientCategoryView,
    selectedIngredients: MutableState<List<IngredientView>>,
    onEvent: (FilterEvent) -> Unit,
) {
    Spacer(modifier = Modifier.height(12.dp))
    TextTitle(text = ingredientCategory.name)
    Spacer(modifier = Modifier.height(12.dp))
    FlowRow(Modifier.fillMaxWidth()) {
        for (ingredient in ingredientCategory.ingredientViews) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .padding(bottom = 12.dp)
            ) {
                FilterItemWithMore(ingredient.name,
                    selectedIngredients.value.contains(ingredient),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredient(ingredient)) })
                { onEvent(FilterEvent.EditOrDeleteIngredient(ingredient)) }
            }
        }
    }
}

@Composable
fun FilterItemWithMore(
    text: String,
    isActive: Boolean,
    isIngredient: Boolean,
    click: () -> Unit,
    more: () -> Unit
) {
    Row(
        Modifier
            .background(
                if (isActive) {
                    if (isIngredient) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                } else MaterialTheme.colorScheme.background,
                RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp).padding(start = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody(
            text, modifier = Modifier
                .clickNoRipple(click)
        )
        Spacer(Modifier.width(6.dp))
        Icon(painterResource(R.drawable.more), "", tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickNoRipple(more))
    }
}

@Composable
fun Modifier.clickNoRipple(click: () -> Unit):Modifier{
    return this.clickable(onClick = click,
        interactionSource = remember { MutableInteractionSource() },
        indication = null)
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    WeekOnAPlateTheme {
        FilterScreen(FilterUIState().apply {
            searchText.value = "По"
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

            resultSearchTags.value = mutableListOf<RecipeTagView>().toMutableList().apply {
                tags.map { it -> it.tags }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
            resultSearchIngredients.value = mutableListOf<IngredientView>().apply {
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
