package week.on.a.plate.dialogs.chooseIngredientsForStep.event


import week.on.a.plate.core.Event

sealed interface ChooseIngredientsForStepEvent : Event {
    object Done : ChooseIngredientsForStepEvent
    object Close : ChooseIngredientsForStepEvent
    class ClickToIngredient(val indIngredient: Long) : ChooseIngredientsForStepEvent
}