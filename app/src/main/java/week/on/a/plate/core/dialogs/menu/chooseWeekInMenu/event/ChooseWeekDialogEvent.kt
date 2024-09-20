package week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.event

import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class ChooseWeekDialogEvent:Event() {
    data object Done: ChooseWeekDialogEvent()
    data object Close: ChooseWeekDialogEvent()
}