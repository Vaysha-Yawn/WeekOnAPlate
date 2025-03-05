package week.on.a.plate.dialogs.forSearchScreen.filtersMore.event


import week.on.a.plate.core.Event

sealed class FiltersMoreEvent: Event() {
    data object Close: FiltersMoreEvent()
}