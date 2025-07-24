package week.on.a.plate.screens.additional.filters.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode

@Serializable
data class FilterDestination(
    val mode: FilterMode, val enum: FilterEnum,
    val lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?,
    val isForCategory: Boolean,
)