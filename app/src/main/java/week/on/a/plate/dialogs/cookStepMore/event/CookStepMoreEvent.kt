package week.on.a.plate.dialogs.cookStepMore.event


import week.on.a.plate.core.Event

sealed interface CookStepMoreEvent : Event {
    object ChangeStartRecipeTime : CookStepMoreEvent
    object ChangeEndRecipeTime : CookStepMoreEvent
    object Close : CookStepMoreEvent
    object ChangePortionsCount : CookStepMoreEvent
    object Delete : CookStepMoreEvent
}