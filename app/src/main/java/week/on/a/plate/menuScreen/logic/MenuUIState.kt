package week.on.a.plate.menuScreen.logic

import week.on.a.plate.core.data.week.WeekView

sealed class MenuUIState {
    data object EmptyWeek:MenuUIState()
    data class Success(val week: WeekView):MenuUIState()
    data class Error(val message: String):MenuUIState()
    data object Loading:MenuUIState()
}