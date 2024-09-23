package week.on.a.plate.core.dialogs.menu.editPositionIngredient.event


import week.on.a.plate.core.Event

sealed class EditPositionIngredientEvent: Event() {
    data object Done: EditPositionIngredientEvent()
    data object Close: EditPositionIngredientEvent()
    data object  ChooseIngredient: EditPositionIngredientEvent()
}