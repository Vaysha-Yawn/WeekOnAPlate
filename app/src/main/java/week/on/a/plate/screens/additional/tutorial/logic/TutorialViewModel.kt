package week.on.a.plate.screens.additional.tutorial.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.app.mainActivity.event.BackNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.event.NavigateBackDest
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.screens.additional.tutorial.event.TutorialEvent
import week.on.a.plate.screens.additional.tutorial.state.TutorialEnum
import week.on.a.plate.screens.additional.tutorial.state.TutorialStateUI
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor() : ViewModel() {

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    val stateUI = TutorialStateUI()

    fun onEvent(event: TutorialEvent) {
        when (event) {
            TutorialEvent.Done -> done()
            TutorialEvent.LastPage -> lastPage()
            TutorialEvent.NextPage -> nextPage()
            TutorialEvent.Skip -> done()
            is TutorialEvent.SelectPage -> selectPage(event.i)
        }
    }

    private fun selectPage(i: Int) {
        stateUI.activePageInd.intValue = i
    }

    private fun nextPage() {
        if (stateUI.activePageInd.intValue + 1 != stateUI.tutorialEnum.value.pages.size) {
            stateUI.activePageInd.intValue += 1
        }
    }

    private fun lastPage() {
        if (stateUI.activePageInd.intValue >= 1) {
            stateUI.activePageInd.intValue -= 1
        }
    }

    fun done() {
        mainEvent.value = MainEvent.Navigate(NavigateBackDest, BackNavParams)
    }

    fun launch(dest: TutorialEnum) {
        stateUI.tutorialEnum.value = dest
        stateUI.activePageInd.intValue = 0
    }
}