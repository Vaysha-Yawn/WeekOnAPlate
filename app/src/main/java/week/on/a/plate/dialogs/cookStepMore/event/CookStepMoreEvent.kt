package week.on.a.plate.dialogs.cookStepMore.event


import week.on.a.plate.core.Event

sealed class CookStepMoreEvent: Event() {
    data object ChangeStartRecipeTime: CookStepMoreEvent()
    data object ChangeEndRecipeTime: CookStepMoreEvent()
    data object IncreaseStepTime: CookStepMoreEvent()
    data object MoveStepByTimeStart: CookStepMoreEvent()
    data object Close: CookStepMoreEvent()
    data object ChangeNumberOfServings: CookStepMoreEvent()
    data object Delete: CookStepMoreEvent()
}