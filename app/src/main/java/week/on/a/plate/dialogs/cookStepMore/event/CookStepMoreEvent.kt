package week.on.a.plate.dialogs.cookStepMore.event


import week.on.a.plate.core.Event

sealed class CookStepMoreEvent: Event() {
    data object ChangeStartRecipeTime: CookStepMoreEvent()
    data object ChangeEndRecipeTime: CookStepMoreEvent()
    data object Close: CookStepMoreEvent()
    data object ChangePortionsCount: CookStepMoreEvent()
    data object Delete: CookStepMoreEvent()
}