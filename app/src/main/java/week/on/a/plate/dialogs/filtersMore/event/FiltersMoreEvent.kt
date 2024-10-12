package week.on.a.plate.dialogs.filtersMore.event


import week.on.a.plate.core.Event

sealed class FiltersMoreEvent: Event() {
    data object Close: FiltersMoreEvent()
    data object CheckFavorite: FiltersMoreEvent()
    data class SetAllCookTime(val timeMin: Int) : FiltersMoreEvent()
    data class SetPrepCookTime(val timeMin: Int) : FiltersMoreEvent()
}