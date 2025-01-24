package week.on.a.plate.screens.filters.view

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterUIState

fun LazyListScope.tag(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if ((stateUI.activeFilterTabIndex.intValue == 0 && stateUI.filterEnum.value == FilterEnum.IngredientAndTag)
        || stateUI.filterEnum.value == FilterEnum.Tag
    ) {
        if (stateUI.searchText.value != "") {
            items(stateUI.resultSearchTags.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.resultSearchTags.value[it].tagName,
                    stateUI.selectedIngredients.value.contains(stateUI.resultSearchIngredients.value[it]),
                    false,
                    click = { onEvent(FilterEvent.SelectTag(stateUI.resultSearchTags.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteTag(
                            context,
                            stateUI.resultSearchTags.value[it]
                        )
                    )
                }
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
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesTags(
    tagCategoryView: TagCategoryView,
    selectedTags: MutableState<List<RecipeTagView>>,
    onEvent: (FilterEvent) -> Unit,
) {
    val context = LocalContext.current
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
                { onEvent(FilterEvent.EditOrDeleteTag(context, tag)) }
            }
        }
    }
}