package week.on.a.plate.menuScreen.state

import week.on.a.plate.core.data.week.WeekView

sealed class WeekState {
    data object EmptyWeek : WeekState()
    data class Success(val week: WeekView) : WeekState()
    data class Error(val message: String) : WeekState()
    data object Loading : WeekState()
}