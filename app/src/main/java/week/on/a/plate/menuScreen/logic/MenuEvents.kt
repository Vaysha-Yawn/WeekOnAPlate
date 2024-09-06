package week.on.a.plate.menuScreen.logic

import week.on.a.plate.core.data.week.WeekView

sealed class MenuEvents {
    data class ChangeDay(val id: Long) : MenuEvents()
    data class getCheckState(val id: Long) : MenuEvents()
}