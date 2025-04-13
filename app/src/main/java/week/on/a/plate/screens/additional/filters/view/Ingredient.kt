package week.on.a.plate.screens.additional.filters.view

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterUIState

fun LazyListScope.ingredient(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if ((stateUI.activeFilterTabIndex.intValue == 1 && stateUI.filterEnum.value == FilterEnum.IngredientAndTag)
        || stateUI.filterEnum.value == FilterEnum.Ingredient
    ) {
        if (stateUI.searchText.value != "") {
            items(
                items = stateUI.resultSearchIngredients.value,
                key = { it.ingredientId }) { ingredient ->
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    ingredient.name,
                    stateUI.selectedIngredients.value.contains(ingredient),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredient(ingredient)) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredient(
                            context,
                            ingredient
                        )
                    )
                }
            }
        } else {
            items(
                items = stateUI.allIngredientsCategories.value,
                key = { it.id }) { ingredientCategory ->
                IngredientCategories(
                    ingredientCategory,
                    stateUI.selectedIngredients, onEvent
                )
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
    val context = LocalContext.current
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
                { onEvent(FilterEvent.EditOrDeleteIngredient(context, ingredient)) }
            }
        }
    }
}