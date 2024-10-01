package week.on.a.plate.dialogAddIngredient.event


import week.on.a.plate.core.Event

sealed class AddIngredientEvent: Event() {
    data object Done: AddIngredientEvent()
    data object Close: AddIngredientEvent()
    data object ChooseCategory: AddIngredientEvent()
}