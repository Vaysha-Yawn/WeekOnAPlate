package week.on.a.plate.screens.additional.filters.view

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
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
            items(stateUI.resultSearchTagsCategories.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.resultSearchTagsCategories.value[it].name,
                    stateUI.selectedTagsCategories.value.contains(stateUI.resultSearchTagsCategories.value[it]),
                    false,
                    click = { onEvent(FilterEvent.SelectTagCategory(stateUI.resultSearchTagsCategories.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteTagCategory(
                            stateUI.resultSearchTagsCategories.value[it],
                            context
                        ),
                    )
                }
            }
        } else {
            items(stateUI.allTagsCategories.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.allTagsCategories.value[it].name,
                    stateUI.selectedTagsCategories.value.contains(stateUI.allTagsCategories.value[it]),
                    false,
                    click = { onEvent(FilterEvent.SelectTagCategory(stateUI.allTagsCategories.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteTagCategory(
                            stateUI.allTagsCategories.value[it],
                            context
                        )
                    )
                }
            }
        }
    }
}