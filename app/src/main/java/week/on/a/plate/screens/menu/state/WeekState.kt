package week.on.a.plate.screens.menu.state

import week.on.a.plate.data.dataView.week.WeekView

sealed class WeekState {
    data object EmptyWeek : WeekState()
    data class Success(val week: WeekView) : WeekState()
    data class Error(val message: String) : WeekState()
    data object Loading : WeekState()
}