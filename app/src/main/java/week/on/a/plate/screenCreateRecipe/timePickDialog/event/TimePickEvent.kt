package week.on.a.plate.screenCreateRecipe.timePickDialog.event


import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogExampleStructure.dialogAbstract.event.DialogEvent

sealed class TimePickEvent: Event() {
    data object Done: TimePickEvent()
    data object Close: TimePickEvent()
}