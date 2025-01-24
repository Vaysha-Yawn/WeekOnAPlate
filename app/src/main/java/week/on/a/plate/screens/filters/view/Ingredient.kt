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
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterUIState

fun LazyListScope.ingredient(
    stateUI: FilterUIState,
    onEvent: (FilterEvent) -> Unit,
    context: Context
) {
    if ((stateUI.activeFilterTabIndex.intValue == 1 && stateUI.filterEnum.value == FilterEnum.IngredientAndTag)
        || stateUI.filterEnum.value == FilterEnum.Ingredient
    ) {
        if (stateUI.searchText.value != "") {
            items(stateUI.resultSearchIngredients.value.size) {
                Spacer(modifier = Modifier.height(24.dp))
                FilterItemWithMore(
                    stateUI.resultSearchIngredients.value[it].name,
                    stateUI.selectedIngredients.value.contains(stateUI.resultSearchIngredients.value[it]),
                    true,
                    click = { onEvent(FilterEvent.SelectIngredient(stateUI.resultSearchIngredients.value[it])) })
                {
                    onEvent(
                        FilterEvent.EditOrDeleteIngredient(
                            context,
                            stateUI.resultSearchIngredients.value[it]
                        )
                    )
                }
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