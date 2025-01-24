package week.on.a.plate.screens.filters.view

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.CreateTagOrIngredient
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.filters.state.FilterUIState

fun LazyListScope.createFilterIfEmpty(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if (stateUI.searchText.value != "") {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            if (checkNotHaveFilter(stateUI)) {
                CreateTagOrIngredient(stateUI.searchText.value) {
                    onEvent(FilterEvent.CreateActive(context))
                }
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