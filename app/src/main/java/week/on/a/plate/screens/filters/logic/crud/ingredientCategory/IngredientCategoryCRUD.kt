package week.on.a.plate.screens.filters.logic.crud.ingredientCategory

import javax.inject.Inject

class IngredientCategoryCRUD @Inject constructor(
    val createIngredientCategory: CreateIngredientCategory,
    val deleteIngredientCategory: DeleteIngredientCategory,
    val editIngredientCategory: EditIngredientCategory
)
