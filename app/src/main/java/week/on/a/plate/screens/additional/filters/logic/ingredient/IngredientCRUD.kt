package week.on.a.plate.screens.additional.filters.logic.ingredient

import javax.inject.Inject

class IngredientCRUD @Inject constructor(
    val createIngredient: CreateIngredient,
    val deleteIngredient: DeleteIngredient,
    val editIngredient: EditIngredient
)
