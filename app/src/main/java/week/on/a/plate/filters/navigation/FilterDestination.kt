package week.on.a.plate.filters.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

sealed class FilterRoute{
    @Serializable
    class FilterDestination(val currentTags:List<RecipeTagView>, val currentIngredients:List<IngredientView>,):
        FilterRoute()

    @Serializable
    class FilterToCreateDraftWithSelIdDestination(val selId:Long): FilterRoute()
}
