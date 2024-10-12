package week.on.a.plate.dialogs.sortMore.event


import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.appDialogExampleStructure.dialogAbstract.event.DialogEvent

sealed class SortMoreEvent: Event() {
    data object AlphabetNormal: SortMoreEvent()
    data object AlphabetRevers: SortMoreEvent()
    data object DateFromOldToNew: SortMoreEvent()
    data object DateFromNewToOld: SortMoreEvent()
    data object Random: SortMoreEvent()
    data object Close: SortMoreEvent()
}