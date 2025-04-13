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

fun LazyListScope.categoryIngredient(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if (stateUI.filterEnum.value == FilterEnum.CategoryIngredient) {
        if (stateUI.searchText.value != "") {
            items(
                items = stateUI.resultSearchIngredientsCategories.value,
                key = { it.id }) { ingredientCategory ->
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    ingredientCategory.name,
                    stateUI.selectedIngredientsCategories.value.contains(
                        ingredientCategory
                    ),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredientCategory(ingredientCategory)) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredientCategory(
                            ingredientCategory,
                            context
                        )
                    )
                }
            }
        } else {
            items(
                items = stateUI.allIngredientsCategories.value,
                key = { it.id }) { ingredientCategory ->
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    ingredientCategory.name,
                    stateUI.selectedIngredientsCategories.value.contains(ingredientCategory),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredientCategory(ingredientCategory)) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredientCategory(
                            ingredientCategory,
                            context
                        )
                    )
                }
            }
        }
    }
}