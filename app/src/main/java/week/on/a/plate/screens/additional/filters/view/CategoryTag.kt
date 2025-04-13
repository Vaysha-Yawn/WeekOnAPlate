package week.on.a.plate.screens.additional.filters.view

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterUIState

fun LazyListScope.categoryTag(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if (stateUI.filterEnum.value == FilterEnum.CategoryTag) {
        if (stateUI.searchText.value != "") {
            items(
                items = stateUI.resultSearchTagsCategories.value,
                key = { it.id }) { tagCategory ->
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    tagCategory.name,
                    stateUI.selectedTagsCategories.value.contains(tagCategory),
                    false,
                    click = { onEvent(FilterEvent.SelectTagCategory(tagCategory)) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteTagCategory(
                            tagCategory,
                            context
                        ),
                    )
                }
            }
        } else {
            items(items = stateUI.allTagsCategories.value, key = { it.id }) { tagCategory ->
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    tagCategory.name,
                    stateUI.selectedTagsCategories.value.contains(tagCategory),
                    false,
                    click = { onEvent(FilterEvent.SelectTagCategory(tagCategory)) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteTagCategory(
                            tagCategory,
                            context
                        )
                    )
                }
            }
        }
    }
}