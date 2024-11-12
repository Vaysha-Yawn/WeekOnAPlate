package week.on.a.plate.dialogs.chooseIngredientsForStep.event


import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.appDialogExampleStructure.dialogAbstract.event.DialogEvent

sealed class ChooseIngredientsForStepEvent: Event() {
    data object Done: ChooseIngredientsForStepEvent()
    data object Close: ChooseIngredientsForStepEvent()
    data class ClickToIngredient(val indIngredient: Long): ChooseIngredientsForStepEvent()
}