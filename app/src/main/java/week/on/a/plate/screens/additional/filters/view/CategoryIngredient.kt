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

fun LazyListScope.categoryIngredient(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if (stateUI.filterEnum.value == FilterEnum.CategoryIngredient) {
        if (stateUI.searchText.value != "") {
            items(stateUI.resultSearchIngredientsCategories.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.resultSearchIngredientsCategories.value[it].name,
                    stateUI.selectedIngredientsCategories.value.contains(
                        stateUI.resultSearchIngredientsCategories.value[it]
                    ),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredientCategory(stateUI.resultSearchIngredientsCategories.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredientCategory(
                            stateUI.resultSearchIngredientsCategories.value[it],
                            context
                        )
                    )
                }
            }
        } else {
            items(stateUI.allIngredientsCategories.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.allIngredientsCategories.value[it].name,
                    stateUI.selectedIngredientsCategories.value.contains(stateUI.allIngredientsCategories.value[it]),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredientCategory(stateUI.allIngredientsCategories.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredientCategory(
                            stateUI.allIngredientsCategories.value[it],
                            context
                        )
                    )
                }
            }
        }
    }
}