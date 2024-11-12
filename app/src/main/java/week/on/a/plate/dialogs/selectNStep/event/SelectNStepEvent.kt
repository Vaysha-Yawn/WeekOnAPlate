package week.on.a.plate.dialogs.selectNStep.event


import week.on.a.plate.core.Event

sealed class SelectNStepEvent: Event() {
    data class Select(val stepInd:Int): SelectNStepEvent()
    data object Close: SelectNStepEvent()
}