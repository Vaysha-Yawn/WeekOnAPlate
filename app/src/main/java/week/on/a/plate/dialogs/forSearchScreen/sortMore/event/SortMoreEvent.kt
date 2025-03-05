package week.on.a.plate.dialogs.forSearchScreen.sortMore.event


import week.on.a.plate.core.Event

sealed class SortMoreEvent: Event() {
    data object AlphabetNormal: SortMoreEvent()
    data object AlphabetRevers: SortMoreEvent()
    data object DateFromOldToNew: SortMoreEvent()
    data object DateFromNewToOld: SortMoreEvent()
    data object Random: SortMoreEvent()
    data object Close: SortMoreEvent()
}