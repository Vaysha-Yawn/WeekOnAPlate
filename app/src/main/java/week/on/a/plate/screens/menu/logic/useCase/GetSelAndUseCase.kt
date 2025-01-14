package week.on.a.plate.screens.menu.logic.useCase

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.event.NavFromMenuData
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionResult
import javax.inject.Inject

class GetSelAndUseCase @Inject constructor() {
    private fun specifyDate(mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,  use: (SpecifySelectionResult) -> Unit) {
        mainViewModel.viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.SpecifySelection))
            vm.launchAndGet(use)
        }
    }

    fun getSelAndCreate(context: Context, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            onEvent(MenuEvent.CreatePosition(res.selId, context))
        }
    }

    fun getSelAndMove(position: Position, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.MovePositionInMenuDB(
                        res.selId,
                        position
                    )
                )
            )
        }
    }

    fun getSelAndDouble(position: Position, mainViewModel: MainViewModel, onEvent:(MenuEvent)->Unit,) {
        specifyDate(mainViewModel, onEvent) { res ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.DoublePositionInMenuDB(
                        res.selId,
                        position
                    )
                )
            )
        }
    }
}
