package week.on.a.plate.core.dialogs.addIngrdient.event


import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class AddIngredientEvent:Event() {
    data object Done: AddIngredientEvent()
    data object Close: AddIngredientEvent()
    data object ChooseCategory: AddIngredientEvent()
}