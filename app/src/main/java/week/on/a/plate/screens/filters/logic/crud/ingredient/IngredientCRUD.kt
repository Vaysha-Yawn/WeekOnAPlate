package week.on.a.plate.screens.filters.logic.crud.ingredient

import javax.inject.Inject

class IngredientCRUD @Inject constructor(
    val createIngredient: CreateIngredient,
    val deleteIngredient: DeleteIngredient,
    val editIngredient: EditIngredient
)
